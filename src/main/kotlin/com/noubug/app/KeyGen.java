/*package com.noubug.app;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGen {
    public static void main(String... args) throws NoSuchAlgorithmException {
        KeyGenerator key = KeyGenerator.getInstance("AES"); key.init(256);

        SecretKey s = key.generateKey();
        byte[] raw = s.getEncoded();

        System.out.print(Base64.getDecoder().decode("Ty3Vcroy9tAwz9IpZ87q4b+Y2xpkyrv1irm1j3rV8lM=".getBytes(Charset.forName("utf8"))).length);
    }
}*/
