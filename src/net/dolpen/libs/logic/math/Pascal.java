package net.dolpen.libs.logic.math;

public class Pascal {

    /**
     * n段のパスカルの三角形(確率)を返します
     * @param n 段数
     * @return 三角形(各段の総和は1)
     */
    public static double[][] ptriangle(int n) {
        if (n == 0)
            return new double[0][0];
        double[][] field = new double[n + 2][n + 1];
        int l = n + 1;
        field[0][0] = 1.0;
        for (int i = 1; i <= l; i++) {
            field[i][0] = field[i - 1][0] / 2;
            for (int j = 1; j < l; j++) {
                field[i][j] = (field[i - 1][j - 1] + field[i - 1][j]) / 2;
            }
        }
        return field;
    }

    /**
     * n段のパスカルの三角形を返します
     * @param n 段数
     * @return 三角形 [n][r]がnCr
     */
    public static long[][] triangle(int n) {
        if (n == 0)
            return new long[0][0];
        long[][] field = new long[n + 2][n + 1];
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
