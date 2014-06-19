package net.dolpen.libs.logic.string;

/**
 * サロゲートペアを意識したUTF-8文字種のチェックとフィルタリング
 */
public class CodePoints {

    private static final int[] MAX_CODE = new int[]{0x007f, 0x07ff, 0xffff, 0x1ffff, 0x1fffff, 0x7ffffff};

    /**
     * そのUnicodeのバイト数を求めます
     *
     * @param charCode Unicode
     * @return バイト数
     */
    public static int getByteLength(int charCode) {
        for (int i = 0; i < MAX_CODE.length; i++)
            if (charCode <= MAX_CODE[i]) return i + 1;
        return -1;
    }

    /**
     * 文字列中のUTF-8で最大バイト数になる文字のバイト数を返す
     *
     * @param str 文字列
     * @return バイト数
     */
    public static int maxByteLength(String str) {
        int l = str.length();
        int max = 1;
        for (int i = 0; i < l; ) {
            int code = str.codePointAt(i);
            int bl = getByteLength(code);
            if (bl < 0) return -1;
            max = Math.max(max, bl);
            i += Character.charCount(code);
        }
        return max;
    }


    /**
     * 文字列中のUTF-8で指定バイト数以上の文字を置換して返す
     *
     * @param str 文字列
     * @param length バイト数
     * @return 変換済み文字列
     */
    public static String replaceByByteLength(String str, int length, String replace) {
        StringBuilder sb = new StringBuilder();
        int l = str.length();
        boolean delete = replace == null || replace.isEmpty();
        for (int i = 0; i < l; ) {
            int code = str.codePointAt(i);
            int bl = getByteLength(code);
            if (bl < 0) return null;
            if (bl >= length) {
                if (!delete) sb.append(replace);
            } else {
                sb.append(Character.toChars(code));
            }
            i += Character.charCount(code);
        }
        return sb.toString();
    }

    /**
     * 文字列中のUTF-8で指定バイト数以上の文字を消去して返す
     *
     * @param str 文字列
     * @param length バイト数
     * @return 変換済み文字列
     */
    public static String deleteByByteLength(String str, int length) {
        return replaceByByteLength(str, length, null);
    }

}
