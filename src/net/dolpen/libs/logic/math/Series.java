package net.dolpen.libs.logic.math;

public class Series {

    /**
     * Σiを求めます
     *
     * @param n 最大値
     * @return Σi (i:1->n)
     */

    public static long sigma_i(long n) {
        return n * (n + 1) / 2;
    }

}
