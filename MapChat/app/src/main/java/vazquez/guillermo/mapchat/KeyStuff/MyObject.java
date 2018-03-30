package vazquez.guillermo.mapchat.KeyStuff;

/**
 * Created by guillermo on 3/6/18.
 */

public class MyObject{
    String publicKey;
    String privateKey;

    public MyObject(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
