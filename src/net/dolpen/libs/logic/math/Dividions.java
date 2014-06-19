package net.dolpen.libs.logic.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 主に割り算系
 */
public class Dividions {

    /**
     * 約数の列挙(未整列、nが小さければ使える) O(sqrt(n))
     */
    public static List<Integer> divisor(int n) {
        List<Integer> ans = new ArrayList<Integer>();
        for (int i = 1; i * i <= n; i++) {
            if (n % i != 0) continue;
            ans.add(i);
            if (i * i != n) ans.add(n / i);
        }
        return ans;
    }

    /**
     * denomの逆数の小数表現における循環節の長さを得る
     *
     * @param denom 分母
     * @return 小数表現における循環節の長さを得る
     */
    public static int getRecurringLength(int denom) {
        int t = denom;
        while (denom % 2 == 0) denom /= 2;
        while (denom % 5 == 0) denom /= 5;
        for (int i = 1; i < t; i++) {
            if (modPow(10, i, denom) == 1) return i;
        }
        return 0;
    }

    /**
     * xのp乗をdで割ったときの余りを求める (非再帰)
     *
     * @param x
     * @param p
     * @param d
     * @return (x^p)%d
     */
    public static int modPow(int x, int p, int d) {
        int r = 1;
        for (int i = Integer.SIZE - 1; i >= 0; i--) {
            r *= r;
            if ((p & 1 << i) > 0)
                r *= x;
            r %= d;
        }
        return r;
    }

    /**
     * xのp乗をdで割ったときの余りを求める（再帰）
     *
     * @param x
     * @param p
     * @param d
     * @return (x^p)%d
     */
    public static int modPow2(int x, int p, int d) {
        if (p == 0) return 1;
        if (p == 1) return x;
        int tmp = modPow(x, p / 2, d);
        tmp = (tmp * tmp) % d;
        if (p % 2 == 1) tmp = (tmp * x) % d;
        return tmp;
    }

}
