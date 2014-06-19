package net.dolpen.libs.logic.encoder;

/**
 * ビット操作用の中間データ
 */
public class BitStream {

    private byte[] src;

    private int pos; // src bit position

    private int length; // src bit length

    public BitStream(byte[] source) {
        src = source;
        init();
    }

    public BitStream(int capacity) {
        src = new byte[capacity];
        init();
    }

    private void init() {
        length = src.length * 8;
        pos = 0;
    }

    public byte[] read(int offset, int count) {
        // Temporary position cursor
        int tempPos = pos + offset;
        Cursor writer = new Cursor(0);
        Cursor reader = new Cursor(tempPos);
        int to = Math.min(pos + offset + count, length);
        byte[] buffer = new byte[to - tempPos];
        while (tempPos < to) {
            if ((((int) src[reader.bytePos]) & (0x1 << (7 - reader.byteMod))) != 0) {
                buffer[writer.bytePos] = (byte) ((int) (buffer[writer.bytePos]) | (0x1 << (7 - writer.byteMod)));
            } else {
                buffer[writer.bytePos] = (byte) ((int) (buffer[writer.bytePos]) & (0xffffffff - (0x1 << (7 - writer.byteMod))));
            }
            // Increment position cursors
            tempPos++;
            reader.next();
            writer.next();
        }
        pos = tempPos;
        return buffer;
    }


    public void write(byte[] buffer, int offset, int count) {
        // Temporary position cursor
        int tempPos = pos;
        Cursor reader = new Cursor(offset);
        Cursor writer = new Cursor(tempPos);
        int to = Math.min(pos + count, length);
        while (tempPos < to) {
            // Copy the bit from buffer to the stream
            if ((((int) buffer[reader.bytePos]) & (0x1 << (7 - reader.byteMod))) != 0) {
                src[writer.bytePos] = (byte) ((int) (src[writer.bytePos]) | (0x1 << (7 - writer.byteMod)));
            } else {
                src[writer.bytePos] = (byte) ((int) (src[writer.bytePos]) & (0xffffffff - (0x1 << (7 - writer.byteMod))));
            }
            // Increment position cursors
            tempPos++;
            reader.next();
            writer.next();
        }
        // write back pos;
        pos = tempPos;
    }

    public void seekCurrent(int offset) {
        pos += offset;
    }

    public int getPosition() {
        return pos;
    }

    public int getLength() {
        return length;
    }

    public byte[] getSrc() {
        return src;
    }

    static class Cursor {
        public int bytePos;

        public int byteMod; // in-bte bit position

        public Cursor(int bitPos) {
            // byte position
            bytePos = bitPos >> 3;
            // in-byte position
            byteMod = bitPos - ((bitPos >> 3) << 3);
        }

        public void next() {
            if (byteMod == 7) {
                byteMod = 0;
                bytePos++;
            } else {
                byteMod++;
            }
        }
    }
}