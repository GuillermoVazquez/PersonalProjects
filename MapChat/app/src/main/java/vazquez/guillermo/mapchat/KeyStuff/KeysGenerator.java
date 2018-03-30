package vazquez.guillermo.mapchat.KeyStuff;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Created by guillermo on 3/6/18.
 */

public class KeysGenerator {
    KeyPair keyPair;

    public KeyPair getKeyPair() {
        return keyPair;
    }

    //this class will generate the keypair
    public void keysSmith(){
        KeyPair kp;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            kp = keyGen.genKeyPair();
            keyPair = kp;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
