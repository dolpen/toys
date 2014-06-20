package net.dolpen.libs.logic.encoder;

/**
 * Base62
 */
public class Base62 {

    private static String Base62CodingSpace = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static byte[] decode(String base62) {
        char[] cs = base62.toCharArray();
        int l = base62.length();
        BitStream stream = new BitStream((l * 6) / 8);
        for (char c : cs) {
            // Look up coding table
            int index = Base62CodingSpace.indexOf(c);
            // If end is reached
            // If 60 or 61 then only write 5 bits to the stream, otherwise 6 bits.
            if (index == 60) {
                stream.write(new byte[]{(byte) 0xf0}, 0, 5);
            } else if (index == 61) {
                stream.write(new byte[]{(byte) 0xf8}, 0, 5);
            } else {
                stream.write(new byte[]{(byte) index}, 2, 6);
            }
        }
        return stream.getSrcToPosition();
    }

    public static String encode(byte[] original) {
        StringBuilder sb = new StringBuilder();
        BitStream stream = new BitStream(original);         // Set up the BitStream
        while (true) {
            byte[] read = stream.read(0, 6); // Try to read 6 bits
            if (read.length == 0) break;
            int code = (read[0] & 0xff) >> 2; // to unsigned byte value
            int check = code >> 1;
                if (check == 0x1f) {
                    sb.append(Base62CodingSpace.charAt(61));
                    stream.seekCurrent(-1);
                } else if (check == 0x1e) {
                    sb.append(Base62CodingSpace.charAt(60));
                    stream.seekCurrent(-1);
                } else {
                    sb.append(Base62CodingSpace.charAt(code));
                }
        }
        return sb.toString();
    }
}