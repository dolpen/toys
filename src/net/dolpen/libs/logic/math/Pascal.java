package net.dolpen.libs.logic.math;

public class Pascal {

    /**
     * n段のパスカルの三角形を返します
     * @param n 段数
     * @return 三角形
     */
    public static long[][] triangle(int n) {
        long[][] field = new long[n + 2][n + 1];
        if (n == 0)
            return field;
        int l = n + 1;
        field[0][0] = 1;
        for (int i = 1; i <= l; i++) {
            field[i][0] = 1;
            for (int j = 1; j < l; j++) {
                field[i][j] = field[i - 1][j - 1] + field[i - 1][j];
            }
        }
        return field;
    }
}
