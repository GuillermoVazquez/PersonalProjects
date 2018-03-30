package vazquez.guillermo.mapchat.KeyStuff;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * Created by guillermo on 3/6/18.
 */

public class Crypto {
    //encrypt using public key because that is industry standard
    public byte[] encryptString(PublicKey publicKey, String string)throws Exception{
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
