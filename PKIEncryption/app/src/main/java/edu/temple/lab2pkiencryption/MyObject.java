package edu.temple.lab2pkiencryption;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by guillermo on 2/18/18.
 */

public class MyObject {
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
