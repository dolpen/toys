package net.dolpen.libs.logic.cypher;

/**
 * http://ja.wikipedia.org/wiki/%E3%82%B7%E3%83%BC%E3%82%B6%E3%83%BC%E6%9A%97%E5%8F%B7
 *
 * @author dolpen
 */
public class Caesar {
    /**
     * シーザー暗号にします
     *
     * @param s    文字列(英小文字のみ)
     * @param diff 差分
     * @return 暗号
     */
    public static String encrypt(String s, int diff) {
        char[] c = s.toLowerCase().toCharArray();
        char[] e = new char[c.length];
        for (int i = 0; i < c.length; i++) {
            if (c[i] < 'a' || c[i] > 'z') {
                e[i] = c[i];
            } else {
                e[i] = (char) ((c[i] - 'a' + diff + 26) % 26 + 'a');
            }
        }
        return new String(e);
    }
}
