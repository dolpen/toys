package net.dolpen.libs.logic.math;

import java.util.Arrays;

public class Matrix {
    /**
     * 全要素がゼロのr行c列の行列を返します
     *
     * @param r 列数
     * @param c 行数
     * @return 行列
     */
    int[][] matrix(int r, int c) {
        int[][] a = new int[r][c];
        for (int i = 0; i < r; i++)
            Arrays.fill(a[i], 0);
        return a;
    }

    /**
     * 行列の乗算
     *
     * @param a 左側
     * @param b 右側
     * @return 乗算結果
     */
    int[][] mul(int[][] a, int[][] b) {
        int[][] c = matrix(a.length, b[0].length);
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                for (int k = 0; k < a[i].length; k++) {
                    c[i][j] += a[i][k] * b[k][j];

                }
            }

        }
        return c;
    }
}
