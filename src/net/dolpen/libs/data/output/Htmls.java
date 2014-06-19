package net.dolpen.libs.data.output;

/**
 * HTMLをなんとかする
 */
public class Htmls {
    /**
     * HTMLエンコード処理。
     */
    public static String encode(String in) {
        if (in == null) return "";
        StringBuilder out = new StringBuilder();
        char[] c = in.toCharArray();
        for (int i = 0; i < c.length; i++) {
            switch (c[i]) {
                case '&':
                    out.append("&amp;");
                    break;
                case '<':
                    out.append("&lt;");
                    break;
                case '>':
                    out.append("&gt;");
                    break;
                case '"':
                    out.append("&quot;");
                    break;
                case '\'':
                    out.append("&#39;");
                    break;
                default:
                    out.append(c[i]);
                    break;
            }
        }
        return out.toString();
    }
}
