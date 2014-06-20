package net.dolpen.libs.logic.encoder;

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
     * base64文字列を復号します
     *
     * @param str base64 string
     * @return 文字列
     */
    public static String decodeStr(String str) {
        return new String(decode(str));
    }


    /**
     * バイト列の暗号化をします
     *
     * @param bytes バイト列
     * @return base64 string
     */
    public static String encode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        BitStream stream = new BitStream(bytes);         // Set up the BitStream
        while (true) {
            byte[] read = stream.read(0, 6); // Try to read 6 bits
            if (read.length == 0) break;
            sb.append(table[(read[0] & 0xff) >> 2]);
        }
        int s = sb.length() % 4;
        for (int i = s == 0 ? 4 : s; i < 4; i++)
            sb.append('=');
        return sb.toString();
    }


    /**
     * base64文字列をバイト列に復号します
     *
     * @param base64 base64 string
     * @return バイト列
     */
    public static byte[] decode(String base64) {
        char[] cs = base64.replaceAll("=", "").toCharArray();
        int l = base64.length();
        BitStream stream = new BitStream((l * 6) / 8);
        for (char c : cs) {
            // Look up coding table
            int k = 0;
            while (k < 64 && table[k] != c) k++;
            if (k == 64) throw new IllegalArgumentException("not valid base62 string");
            stream.write(new byte[]{(byte) k}, 2, 6);
        }
        return stream.getSrcToPosition();
    }
}
