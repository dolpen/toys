package net.dolpen.libs.logic.encoder;

public class Base32 {
    public static final char[] table = "0123456789abcdefghijklmnopqrstuv"
        .toCharArray();

    /**
     * 文字列の暗号化をします
     *
     * @param str 文字列
     * @return base32 string
     */
    public static String encodeStr(String str) {
        return encode(str.getBytes());
    }

    /**
     * base32文字列を復号します
     *
     * @param str base32 string
     * @return 文字列
     */
    public static String decodeStr(String str) {
        return new String(decode(str));
    }


    /**
     * バイト列の暗号化をします
     *
     * @param bytes バイト列
     * @return base32 string
     */
    public static String encode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        BitStream stream = new BitStream(bytes);         // Set up the BitStream
        while (true) {
            byte[] read = stream.read(0, 5); // Try to read 5 bits
            if (read.length == 0) break;
            sb.append(table[(read[0] & 0xff) >> 3]);
        }
        int s = sb.length() % 4;
        for (int i = s == 0 ? 4 : s; i < 4; i++)
            sb.append('=');
        return sb.toString();
    }


    /**
     * base32文字列をバイト列に復号します
     *
     * @param base32 base32 string
     * @return バイト列
     */
    public static byte[] decode(String base32) {
        char[] cs = base32.replaceAll("=", "").toCharArray();
        int l = base32.length();
        BitStream stream = new BitStream((l * 5) / 8);
        for (char c : cs) {
            // Look up coding table
            int k = 0;
            while (k < 32 && table[k] != c) k++;
            if (k == 32) throw new IllegalArgumentException("not valid base32 string");
            stream.write(new byte[]{(byte) k}, 3, 5);
        }
        return stream.getSrcToPosition();
    }
}
