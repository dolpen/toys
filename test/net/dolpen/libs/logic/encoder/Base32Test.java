package net.dolpen.libs.logic.encoder;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Base32Test extends EncoderTest {

    @Test
    public void testEncode() throws Exception {
        for (int i = 0; i < 100; i++) {
            byte[] seed = getRandomTestCase();
            String b32 = Base32.encode(seed);
            byte[] back = Base32.decode(b32);
            assertThat(back, is(seed));
        }
    }


}
