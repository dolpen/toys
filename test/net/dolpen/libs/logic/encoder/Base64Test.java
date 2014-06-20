package net.dolpen.libs.logic.encoder;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Base64Test extends EncoderTest {

    @Test
    public void testEncode() throws Exception {
        for (int i = 0; i < 100; i++) {
            byte[] seed = getRandomTestCase();
            String b64 = Base64.encode(seed);
            byte[] back = Base64.decode(b64);
            assertThat(back, is(seed));
        }
    }






}
