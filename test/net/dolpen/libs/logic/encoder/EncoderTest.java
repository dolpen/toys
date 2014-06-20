package net.dolpen.libs.logic.encoder;

import java.util.Random;

/**
 * エンコーダーのテスト
 */
public abstract class EncoderTest {
    protected Random r = null;

    protected byte[] getRandomTestCase() {
        if (r == null) r = new Random(System.currentTimeMillis());
        int l = r.nextInt(256);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) r.nextInt(256);
        }
        return ret;
    }
}
