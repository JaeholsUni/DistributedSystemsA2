/*
 *
 *
 * The code in this class is heavily informed on the code found at the below website
 * https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/#41-java-pbkdf2withhmacsha1-hash-example
 * */

package com.unimelb;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class passwordHandler {

    private byte[] salt;

    public passwordHandler() {

        SecureRandom random = new SecureRandom();
        this.salt = new byte[16];
        random.nextBytes(salt);
    }

    public byte[] hashPassword(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536,128);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return hash;
        } catch (Exception e) {

        }

        return null;
    }

    public boolean checkPassword(byte[] originalHash, byte[] newHash) {

        int diff = originalHash.length ^ newHash.length;

        for (int i = 0; i < originalHash.length && i < newHash.length; i++) {
            diff |= originalHash[i] ^ newHash[i];
        }

        return diff==0;
    }
}
