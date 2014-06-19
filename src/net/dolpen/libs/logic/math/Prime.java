package net.dolpen.libs.logic.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prime {
    /**
     * n(<=98701786)番目の素数を多めに近似します。
     * 係数を1.15からもう少し下げれば1億までいけそう。
     *
     * @param n
     * @return
     */
    public static int li(int n) {
        double ln = Math.log(n);
        double ln2 = Math.log(Math.log(n));
        double l = (double) n * ln + (double) n * ln2 * 1.15;
        return (int) l;
    }

    /**
     * maxまでの素数判定列をアトキンの篩で求めます。
     *
     * @param max 最大値
     * @return 素数判定列
     */
    public static boolean[] sieveOfAtkin(int max) {
        if (max < 5)
            return Arrays.copyOf(new boolean[]{false, false, true, true, false}, max + 1);
        boolean[] r = new boolean[max + 1];
        Arrays.asList(r, false);
        int sqm = (int) Math.sqrt(max);
        int n;
        for (int z = 1; z <= 5; z += 4) {
            for (int y = z; y <= sqm; y += 6) {
                for (int x = 1; x <= sqm && (n = 4 * x * x + y * y) <= max; ++x)
                    r[n] = !r[n];
                for (int x = y + 1; x <= sqm && (n = 3 * x * x - y * y) <= max; x += 2)
                    r[n] = !r[n];
            }
        }
        for (int z = 2; z <= 4; z += 2) {
            for (int y = z; y <= sqm; y += 6) {
                for (int x = 1; x <= sqm && (n = 3 * x * x + y * y) <= max; x += 2)
                    r[n] = !r[n];
                for (int x = y + 1; x <= sqm && (n = 3 * x * x - y * y) <= max; x += 2)
                    r[n] = !r[n];
            }
        }
        for (int y = 3; y <= sqm; y += 6) {
            for (int z = 1; z <= 2; ++z) {
                for (int x = z; x <= sqm && (n = 4 * x * x + y * y) <= max; x += 3)
                    r[n] = !r[n];
            }
        }
        for (n = 5; n <= sqm; ++n)
            if (r[n])
                for (int k = n * n; k <= max; k += n * n)
                    r[k] = false;
        r[2] = r[3] = true;
        return r;
    }

    /**
     * maxまでの素数判定列をシンプルなエラトステネスの篩で求めます。
     *
     * @param max 最大値
     * @return 素数判定列
     */
    public static boolean[] sieveOfEratosthenes(int max) {
        boolean r[] = new boolean[max + 1];
        r[0] = r[1] = false;
        r[2] = true;
        for (int i = 3; i <= max; i++)
            r[i] = (i % 2 != 0);
        for (int i = 3; i * i <= max; i++) {
            for (int j = i * i; j <= max; j += i)
                r[j] = false;
        }
        return r;
    }

    /**
     * 素数判定 O(sqrt(n))
     */
    public static boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * 素因数分解  O(sqrt(n))
     */
    public static List<Integer> factrization(int n) {
        List<Integer> l = new ArrayList<Integer>();
        for (int x = 2; x * x <= n; x++) {
            while (n % x == 0) {
                n /= x;
                l.add(x);
            }
        }
        if (n > 1) l.add(n);
        return l;
    }
}
