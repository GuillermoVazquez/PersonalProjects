package edu.temple.lab2pkiencryption;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MainActivity extends Activity {

    myContentProvider myCp = new myContentProvider();
    keysGenerator kg = new keysGenerator();
    Converters converters = new Converters();
    crypto crypto = new crypto();
    KeyPair kp;
    PublicKey publicKey;
    PrivateKey privateKey;
    String inputString;
    byte[] encrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //passing mainActivityContext
        myCp.passContext(this);

        //ok first, lets catch when the user clicks the button
        Button button = findViewById(R.id.requestKeyPair);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ok so now lets generate the keys
                //call our key generator class
                kg.keysSmith();
                //lets toast the user so that they know a keypair was generated
                //we have the keypair!!!
                kp = kg.getKeyPair();
                Context context = getApplicationContext();
                CharSequence text = "keys generated!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //lets store them into our cursor matrix
                ContentValues cv = new ContentValues();
                //covert public and private key to string

                cv.put("public",converters.convertPublicString(kp.getPublic()));
                cv.put("private",converters.convertPrivateString(kp.getPrivate()));
                Uri uri = getContentResolver().insert(myContentProvider.CONTENT_URI,cv);


            }
        });

        //now lets get some text from the user input
        //and then encrypt the text
        final EditText editText = findViewById(R.id.editText);
        Button buttonE = findViewById(R.id.encrypt);
        Button buttonD = findViewById(R.id.decrypt);
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputString = editText.getText().toString();
                //now lets encrypt
                //we have to grab the public key from the matrix
                Cursor cursor = getContentResolver().query(myContentProvider.CONTENT_URI,null,null,null,null);
                cursor.moveToFirst();
                int columnOfPublicKey = cursor.getColumnIndex("public");
                String publicKeyString =  cursor.getString(columnOfPublicKey);
                PublicKey pk;
                try {
                    pk = converters.convertPublic(publicKeyString);
                    try{encrypt = crypto.encryptString(pk,inputString);}catch (Exception e){}
                    inputString = encrypt.toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                //lets replace the edittext text with the encrypted string
                editText.setText(inputString);
            }
        });
        //decrypt the message
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(myContentProvider.CONTENT_URI,null,null,null,null);
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
                inputString = new String(encrypt);
                editText.setText(inputString);
            }
        });
    }

}