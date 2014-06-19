package net.dolpen.libs.logic.encoder;

import java.util.BitSet;

public class Base64 {
    public static final char[] table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();

    /**
     * 文字列の暗号化をします
     *
     * @param str 文字列
     * @return base64 string
     */
    public static String encodeStr(String str) {
        return encode(str.getBytes());
    }

    /**
     * バイト列の暗号化をします
     *
     * @param bytes バイト列
     * @return base64 string
     */
    public static String encode(byte[] bytes) {
        int s = bytes.length * 8;
        if (s % 6 > 0)
            s += 6 - (s % 6);
        BitSet bs = new BitSet(bytes.length * 8);
        for (int i = 0; i < bytes.length; i++) {
            int b = (bytes[i] + 256) % 256;
            for (int j = 0; j < 8; j++) {
                bs.set(i * 8 + j, (b & 1 << (7 - j)) > 0);
            }

        }
        for (int i = bytes.length * 8; i < s; i++)
            bs.set(i, false);
        s /= 6;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++) {
            int k = 0;
            for (int j = 0; j < 6; j++)
                if (bs.get(i * 6 + j))
                    k |= 1 << (5 - j);
            sb.append(table[k]);
        }
        s = sb.length() % 4;
        for (int i = s == 0 ? 4 : s; i < 4; i++)
            sb.append('=');
        return sb.toString();
    }

    /**
     * base64文字列を復号します
     *
     * @param str base64 string
     * @return 文字列
     */
    public static String decodeStr(String str) {
        return new String(decode(str));
    }

    /**
     * base64文字列をバイト列に復号します
     *
     * @param str base64 string
     * @return バイト列
     */
    public static byte[] decode(String str) {
        char[] c = str.replaceAll("=", "").toCharArray();
        int s = c.length * 6;
        BitSet bs = new BitSet(s);
        for (int i = 0; i < c.length; i++) {
            int k = 0;
            for (k = 0; k < 63 && table[k] != c[i]; k++) ;
            for (int j = 0; j < 6; j++) {
                bs.set(i * 6 + j, (k & 1 << (5 - j)) > 0);
            }
        }
        s /= 8;
        byte[] bytes = new byte[s];
        for (int i = 0; i < s; i++) {
            int k = 0;
            for (int j = 0; j < 8; j++)
                if (bs.get(i * 8 + j))
                    k |= 1 << (7 - j);
            bytes[i] = (byte) k;
        }
        return bytes;
    }
}
