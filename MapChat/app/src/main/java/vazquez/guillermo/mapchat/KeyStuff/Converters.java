package vazquez.guillermo.mapchat.KeyStuff;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by guillermo on 3/6/18.
 */

public class Converters {
    //pk -> string
    public String convertPublicString(PublicKey publicKey){
        String publicK = Base64.encodeToString(publicKey.getEncoded(),Base64.DEFAULT);
        return publicK;
    }

    //privKey -> string
    public String convertPrivateString(PrivateKey privateKey){
        String privateK = Base64.encodeToString(privateKey.getEncoded(),Base64.DEFAULT);
        return privateK;
    }

    //string -> pubkey
    public PublicKey convertPublic(String pubk) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.decode(pubk,Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        return pubKey;
    }

    //string -> privKey
    public PrivateKey convertPrivate(String privk) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateBytes = Base64.decode(privk, Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey prk = keyFactory.generatePrivate(keySpec);
        return prk;
    }
}
