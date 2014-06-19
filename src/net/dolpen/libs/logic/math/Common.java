package net.dolpen.libs.logic.math;

public class Common {
    /**
     * @param a a
     * @param b b
     * @return 最大公約数
     */
    public static int gcd(int a, int b) {
        while (b > 0) {
            int c = a;
            a = b;
            b = c % b;
        }
        return a;
    }

    /**
     * @param a a
     * @param b b
     * @return 最小公倍数
     */
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * ax+by=gcd(a,b)における、(x,y)のひとつおよびgcd(a, b)を返します。
     *
     * @param a a
     * @param b b
     * @return 拡張ユークリッド互除法
     */
    public static int[] extgcd(int a, int b) {
        int x, y, t, aa, bb;
        aa = a;
        bb = b;
        x = 1;
        y = 0;
        while (a > 0) {
            t = y - (b / a) * x;
            y = x;
            x = t;
            t = b % a;
            b = a;
            a = t;
        }
        return new int[]{((y + bb) % bb), ((x + aa) % aa), b};
    }

    /**
     * @param n n
     * @return オイラーのφ関数
     * @see <a href="http://ja.wikipedia.org/wiki/%E3%82%AA%E3%82%A4%E3%83%A9%E3%83%BC%E3%81%AE%CF%86%E9%96%A2%E6%95%B0">Wikipedia</a>
     */
    int eulerPhi(int n) {
        if (n == 0)
            return 0;
        int r = n;
        for (int x = 2; x * x <= n; ++x) {
            if (n % x == 0) {
                r -= r / x;
                while (n % x == 0)
                    n /= x;
            }
        }
        if (n > 1)
            r -= r / n;
        return r;
    }

    /**
     * @param n n
     * @return カーマイケルのλ関数
     * @see <a href="http://en.wikipedia.org/wiki/Carmichael_function">Wikipedia</a>
     */
    public static int carmichaelLambda(int n) {
        int r = 1;
        if (n % 8 == 0)
            n /= 2;
        for (int d = 2; d <= n; ++d) {
            if (n % d == 0) {
                int y = d - 1;
                n /= d;
                while (n % d == 0) {
                    n /= d;
                    y *= d;
                }
                r = lcm(r, y);
            }
        }
        return r;
    }
}
