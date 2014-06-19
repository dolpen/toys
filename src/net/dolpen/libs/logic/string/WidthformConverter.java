package net.dolpen.libs.logic.string;

/**
 * 文字列半角全角変換回り
 */
public class WidthformConverter {
    /**
     * @param str Lorem ipsum dolor sit amet,
     * @return Ｌｏｒｅｍ　ｉｐｓｕｍ　ｄｏｌｏｒ　ｓｉｔ　ａｍｅｔ，
     */
    public String halfToFullWidth(String str) {
        StringBuilder sb = new StringBuilder(str);
        int l = sb.length();
        for (int i = 0; i < l; i++) {
            int c = (int) sb.charAt(i);
            if ((c >= 0x41 && c <= 0x5A) || (c >= 0x61 && c <= 0x7A)) {
                sb.setCharAt(i, (char) (c + 0xFEE0));
            }
        }
        return sb.toString();
    }

    /**
     * @param str Ｌｏｒｅｍ　ｉｐｓｕｍ　ｄｏｌｏｒ　ｓｉｔ　ａｍｅｔ，
     * @return Lorem ipsum dolor sit amet,
     */
    public String fullToHalfWidth(String str) {
        StringBuilder sb = new StringBuilder(str);
        int l = sb.length();
        for (int i = 0; i < l; i++) {
            int c = (int) sb.charAt(i);
            if ((c >= 0xFF21 && c <= 0xFF3A) || (c >= 0xFF41 && c <= 0xFF5A)) {
                sb.setCharAt(i, (char) (c - 0xFEE0));
            }
        }
        return sb.toString();
    }

}
