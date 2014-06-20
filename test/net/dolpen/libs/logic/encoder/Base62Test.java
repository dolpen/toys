package net.dolpen.libs.logic.encoder;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Base62Test extends EncoderTest {

    @Test
    public void testEncode() throws Exception {
        for (int i = 0; i < 100; i++) {
            byte[] seed = getRandomTestCase();
            String b62 = Base62.encode(seed);
            byte[] back = Base62.decode(b62);
            assertThat(back, is(seed));
        }
    }
}
