package net.dolpen.libs.data.output;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;


public class HtmlsTest {

    @Test
    public void testEncode() throws Exception {
        assertThat(Htmls.encode(null),is(""));
        assertThat(Htmls.encode(""),is(""));
        assertThat(Htmls.encode("<script language=\"javascript\">"),is("&lt;script language=&quot;javascript&quot;&gt;"));
        assertThat(Htmls.encode("&lt;script&gt;"),is("&amp;lt;script&amp;gt;"));
        assertThat(Htmls.encode("<a onclick=\"func('a')\">"),is("&lt;a onclick=&quot;func(&#39;a&#39;)&quot;&gt;"));
    }
}