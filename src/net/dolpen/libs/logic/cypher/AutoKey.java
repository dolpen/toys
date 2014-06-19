package net.dolpen.libs.logic.cypher;

/**
 * http://en.wikipedia.org/wiki/Autokey_cipher
 *
 * @author dolpen
 */
public class AutoKey {
    public static String encrypt(char k, String s) {
        char[] c = s.toLowerCase().toCharArray();
        if (k < 'a' || k > 'z')
            return null;
        char[] e = new char[c.length];
        for (int i = 0; i < c.length; i++) {
            if (c[i] < 'a' || c[i] > 'z') {
                e[i] = c[i];
            } else {
                e[i] = (char) ((c[i] + k - 'a' - 'a') % 26 + 'a');
                k = (char) ((e[i] + k - 'a' - 'a') % 26 + 'a');
            }
        }
        return new String(e);
    }

    public static String decrypt(char k, String s) {
        char[] c = s.toLowerCase().toCharArray();
        char[] e = new char[c.length];
        for (int i = 0; i < c.length; i++) {
            if (c[i] < 'a' || c[i] > 'z') {
                e[i] = c[i];
            } else {
                e[i] = (char) ((c[i] - k + 26) % 26 + 'a');
                k = (char) ((c[i] + k - 'a' - 'a') % 26 + 'a');
            }
        }
        return new String(e);
    }
}
