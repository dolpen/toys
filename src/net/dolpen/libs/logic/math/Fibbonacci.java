package net.dolpen.libs.logic.math;

public class Fibbonacci {
    /**
     * @param max 最大値
     * @return fib[max]までのフィボナッチ数列
     */
    public static int[] fibbonacci(int max) {
        int[] fib = new int[max];
        for (int i = 2; i < max; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }
}
