package net.dolpen.libs.logic.math;

import java.math.BigInteger;

/**
 * コンビネーション
 */
public class Combination {

    public static long calc(int n, int k) {
        if (n < k || n > 20) return -1;
        long ans = 1;
        for (int i = 1; i <= k; i++) {
            ans *= (n - i + 1);
            ans /= i;
        }
        return ans;
    }

    public static BigInteger bcalc(int n, int k) {
        BigInteger ans = BigInteger.ONE;
        for (int i = 1; i <= k; i++) {
            ans = ans.multiply(BigInteger.valueOf(n - i + 1)).divide(BigInteger.valueOf(i));
        }
        return ans;
    }
}
