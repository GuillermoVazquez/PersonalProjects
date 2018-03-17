package edu.temple.nfcbeamencryptedmessage;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import edu.temple.nfcbeamencryptedmessage.KeyStuff.Converters;
import edu.temple.nfcbeamencryptedmessage.KeyStuff.Crypto;

public class Receiver extends Activity implements NfcAdapter.CreateNdefMessageCallback {
    String publicKey;
    Button encrypt;
    EditText message;
    String encryptThis;
    byte[] encryptedMessage;
    Crypto crypto = new Crypto();
    PublicKey pk;
    Converters converters = new Converters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        encrypt = findViewById(R.id.encrypt);
        message = findViewById(R.id.message);

        //encrypt
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the message to encrypt
                encryptThis = message.getText().toString();

                try {
                    //message now in byte[]
                    encryptedMessage = crypto.encryptString(pk,encryptThis);
                    message.setText(encryptedMessage.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //send encrypted message
        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        mAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            //public key is in byte[]
            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            //public key byte[] -> string
            publicKey = new String(message.getRecords()[0].getPayload());
            try {
                pk = converters.convertPublic(publicKey);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }

    }

    //send encrypted message
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        //sending message in byte[]
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", encryptedMessage);
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
}
