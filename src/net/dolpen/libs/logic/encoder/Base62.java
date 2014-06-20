package net.dolpen.libs.logic.encoder;

/**
 * Base62
 */
public class Base62 {

    public static final char[] table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        .toCharArray();

    /**
     * 文字列の暗号化をします
     *
     * @param str 文字列
     * @return base62 string
     */
    public static String encodeStr(String str) {
        return encode(str.getBytes());
    }

    /**
     * base64文字列を復号します
     *
     * @param str base62 string
     * @return 文字列
     */
    public static String decodeStr(String str) {
        return new String(decode(str));
    }

    /**
     * バイト列の暗号化をします
     *
     * @param bytes バイト列
     * @return base62 string
     */
    public static String encode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        BitStream stream = new BitStream(bytes);         // Set up the BitStream
        while (true) {
            byte[] read = stream.read(0, 6); // Try to read 6 bits
            if (read.length == 0) break;
            int code = (read[0] & 0xff) >> 2; // to unsigned byte value
            switch (code >> 1) {
                case 0x1f:
                    sb.append(table[61]);
                    stream.seekCurrent(-1);
                    break;
                case 0x1e:
                    sb.append(table[60]);
                    stream.seekCurrent(-1);
                    break;
                default:
                    sb.append(table[code]);
            }
        }
        return sb.toString();
    }

    /**
     * base62文字列をバイト列に復号します
     *
     * @param base62 base62 string
     * @return バイト列
     */
    public static byte[] decode(String base62) {
        char[] cs = base62.toCharArray();
        int l = base62.length();
        BitStream stream = new BitStream((l * 6) / 8);
        for (char c : cs) {
            // Look up coding table
            int k = 0;
            while (k < 62 && table[k] != c) k++;
            if (k == 62) throw new IllegalArgumentException("not valid base62 string");
            // If 60 or 61 then only write 5 bits to the stream, otherwise 6 bits.
            switch (k) {
                case 60:
                    stream.write(new byte[]{(byte) 0xf0}, 0, 5);
                    break;
                case 61:
                    stream.write(new byte[]{(byte) 0xf8}, 0, 5);
                    break;
                default:
                    stream.write(new byte[]{(byte) k}, 2, 6);
            }
        }
        return stream.getSrcToPosition();
    }
}