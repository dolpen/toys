package net.dolpen.libs.logic.cypher;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class Blowfish {

    protected static SecretKeySpec generateKey(String privateKey, int bits) {
        final byte[] key = new byte[bits / 8];
        final byte[] bites = privateKey.getBytes();
        if (key.length > bites.length) {
            throw new IllegalArgumentException("privateKey は " + key.length
                    + " 以上のバイト数が必要です.");
        }
        System.arraycopy(bites, 0, key, 0, key.length);
        return new SecretKeySpec(key, "Blowfish");
    }

    public static String encrypt(String raw, String privateKey) {
        return encrypt(raw, privateKey, 128);
    }

    public static String encrypt(String raw, String privateKey, int bits) {
        if (raw == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(privateKey, bits));
            byte[] encrypted = cipher.doFinal(raw.getBytes(Charset
                    .forName("UTF-8")));
            return Base64.encode(encrypted);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String decrypt(String encrypted, String privateKey) {
        return decrypt(encrypted, privateKey, 128);
    }

    public static String decrypt(String encrypted, String privateKey, int bits) {
        if (encrypted == null) {
            return null;
        }
        try {
            byte[] encryptedBytes = Base64.decode(encrypted);
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, generateKey(privateKey, bits));
            byte[] decrypted = cipher.doFinal(encryptedBytes);
            return new String(decrypted, Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
