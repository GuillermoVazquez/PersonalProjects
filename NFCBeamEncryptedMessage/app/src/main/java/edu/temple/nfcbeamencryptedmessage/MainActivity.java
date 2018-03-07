package edu.temple.nfcbeamencryptedmessage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import edu.temple.nfcbeamencryptedmessage.KeyStuff.Converters;
import edu.temple.nfcbeamencryptedmessage.KeyStuff.Crypto;
import edu.temple.nfcbeamencryptedmessage.KeyStuff.KeysGenerator;

public class MainActivity extends Activity implements NfcAdapter.CreateNdefMessageCallback{
    MyConentProvider myConentProvider = new MyConentProvider();
    KeysGenerator keysGenerator = new KeysGenerator();
    Converters converters = new Converters();
    Crypto crypto = new Crypto();
    KeyPair keyPair;
    String publicKeyMessage;
    String message;
    byte[] encrypt;
    Button requestKp;
    Button decrypt;
    EditText encryptedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the keypair
        requestKp = findViewById(R.id.requestKeyPair);
        requestKp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keysGenerator.keysSmith();
                keyPair = keysGenerator.getKeyPair();
                Context context = getApplicationContext();
                CharSequence text = "keys generated!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //lets store them into our cursor matrix
                ContentValues cv = new ContentValues();
                //covert public and private key to string
                //key -> string
                publicKeyMessage = converters.convertPublicString(keyPair.getPublic());
                cv.put("public",converters.convertPublicString(keyPair.getPublic()));
                cv.put("private",converters.convertPrivateString(keyPair.getPrivate()));
                Uri uri = getContentResolver().insert(myConentProvider.CONTENT_URI,cv);
            }
        });

        //send public key
        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        mAdapter.setNdefPushMessageCallback(this, this);

        //decrypt message
        decrypt = findViewById(R.id.decrypt);
        encryptedMessage = findViewById(R.id.editText);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(myConentProvider.CONTENT_URI,null,null,null,null);
                cursor.moveToFirst();
                int columnOfPrivateKey = cursor.getColumnIndex("private");
                String privateKeyString =  cursor.getString(columnOfPrivateKey);
                PrivateKey pk;
                try {
                    pk = converters.convertPrivate(privateKeyString);
                    try{encrypt = crypto.decrypt(pk,encrypt);}catch (Exception e){}
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                encryptedMessage.setText(new String(encrypt));
            }
        });

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        //public key String -> byte[]
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", publicKeyMessage.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            //message is in byte[]
            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            encrypt = message.getRecords()[0].getPayload();
            encryptedMessage.setText(encrypt.toString());
        }

    }
}
