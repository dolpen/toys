package net.dolpen.libs.logic.encoder;

/**
 * Base62
 */
public class Base62 {

    private static String Base62CodingSpace = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static byte[] fromBase62(String base62) {
        int count = 0;
        char[] cs = base62.toCharArray();
        int l = base62.length();
        BitStream stream = new BitStream((l * 6) / 8);
        for (char c : cs) {
            // Look up coding table
            int index = Base62CodingSpace.indexOf(c);
            // If end is reached
            if (count == l - 1) {
                // Check if the ending is good
                int mod = stream.getPosition() % 8;
                if (mod > 0) {
                    stream.write(new byte[]{(byte) (index << (mod))}, 0, 8-mod);
                }
            } else {
                // If 60 or 61 then only write 5 bits to the stream, otherwise 6 bits.
                if (index == 60) {
                    stream.write(new byte[]{(byte) 0xf0}, 0, 5);
                } else if (index == 61) {
                    stream.write(new byte[]{(byte) 0xf8}, 0, 5);
                } else {
                    stream.write(new byte[]{(byte) index}, 2, 6);
                }
            }
            count++;
        }
        return stream.getSrc();
    }

    public static String toBase62(byte[] original) {
        StringBuilder sb = new StringBuilder();
        BitStream stream = new BitStream(original);         // Set up the BitStream
        int l = stream.getLength();
        while (true) {
            int from = stream.getPosition();
            byte[] read = stream.read(0, 6); // Try to read 6 bits
            int code = read[0]<0?(256+read[0]):read[0];
            if (stream.getPosition() < l) {
                if ((code >> 3) == 0x1f) {
                    sb.append(Base62CodingSpace.charAt(61));
                    stream.seekCurrent(-1);
                } else if ((code >> 3) == 0x1e) {
                    sb.append(Base62CodingSpace.charAt(60));
                    stream.seekCurrent(-1);
                } else {
                    sb.append(Base62CodingSpace.charAt(code >> 2));
                }
            } else {
                // Padding 0s to make the last bits to 6 bit
                sb.append(Base62CodingSpace.charAt(code >> (8 - (l - from))));
                break;
            }
        }
        return sb.toString();
    }
}