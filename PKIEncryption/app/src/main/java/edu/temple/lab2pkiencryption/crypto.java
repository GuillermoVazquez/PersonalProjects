package edu.temple.lab2pkiencryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by guillermo on 2/12/18.
 */

//this class will encrypt a string that is passed into it
public class crypto {

    //encrypt using public key because that is industry standard
    public byte[] encryptString(PublicKey publicKey,String string)throws Exception{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            return cipher.doFinal(string.getBytes());
    }

    public byte[] decrypt(PrivateKey privateKey, byte[] encrypted)throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        return cipher.doFinal(encrypted);
    }

}
