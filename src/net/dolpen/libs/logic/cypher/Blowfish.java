package net.dolpen.libs.logic.cypher;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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

    public static byte[] encrypt(byte[] raw, String privateKey) {
        return encrypt(raw, privateKey, 128);
    }

    public static byte[] encrypt(byte[] raw, String privateKey, int bits) {
        if (raw == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(privateKey, bits));
            return cipher.doFinal(raw);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] decrypt(byte[] encrypted, String privateKey) {
        return decrypt(encrypted, privateKey, 128);
    }

    public static byte[] decrypt(byte[] encrypted, String privateKey, int bits) {
        if (encrypted == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, generateKey(privateKey, bits));
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
