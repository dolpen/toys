package net.dolpen.libs.data.input;

import org.junit.Test;

import java.io.FileInputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StreamsTest {

    @Test
    public void testToString() throws Exception {
        FileInputStream fi = new FileInputStream("test-resources/utf8.txt");
        assertThat(Streams.toString(fi, "UTF-8"), is("test utf8 with bom"));
    }
}