package net.dolpen.libs.logic.math;

import java.util.Arrays;

/**
 * 統計
 */
public class Statistics {
    /**
     * @param a 数値
     * @return 最小値
     */
    public static double min(double[] a) {
        double min = Double.MAX_VALUE;
        for (double n : a) min = Math.min(min, n);
        return min;
    }

    /**
     * @param a 数値
     * @return 最大値
     */
    public static double max(double[] a) {
        double max = Double.MIN_VALUE;
        for (double n : a) max = Math.max(max, n);
        return max;
    }

    /**
     * @param a 数値
     * @return 中央値
     */
    public static double median(double[] a) {
        double[] x = Arrays.copyOf(a, a.length);
        Arrays.sort(x);
        return x[a.length / 2];
    }

    /**
     * @param a 数値
     * @param n 位置
     * @return n%値
     */
    public static double median(double[] a, double n) {
        double[] x = Arrays.copyOf(a, a.length);
        Arrays.sort(x);
        return x[(int) (a.length * n / 100)];
    }


    /**
     * @param x 数値
     * @return 階乗
     */
    public static double factorial(double x) {
        double h = 0.1, ans = 0.0;
        for (int i = 0; i < 300; i++) {
            double t = h * (i - 32);
            double et = Math.exp(-t);
            double phi = Math.exp(t - et);
            ans += Math.pow(phi, x) * phi * (et + 1) * Math.exp(-phi);
        }
        return ans * h;
    }


    /**
     * @param x 数値
     * @return ガンマ関数
     */
    public static double gamma(double x) {
        return factorial(x - 1);
    }


    /**
     * @param a 数値
     * @return 総和
     */
    public static double sum(double[] a) {
        double ans = 0.0;
        for (double n : a) ans += n;
        return ans;
    }

    /**
     * @param a 数値
     * @return 算術平均
     */
    public static double mean(double[] a) {
        return sum(a) / a.length;
    }

    /**
     * @param x x:独立変数
     * @param y y:従属変数
     * @return 偏差積和
     */
    public static double dot_dev(double[] x, double[] y) {
        double mx = mean(x), my = mean(y);
        double ans = 0.0;
        for (int i = 0; i < x.length; i++)
            ans += (x[i] - mx) * (y[i] - my);
        return ans;
    }

    /**
     * @param x 数値
     * @return 偏差平方和
     */
    public static double devsq(double[] x) {
        return dot_dev(x, x);
    }

    /**
     * @return 共分散
     */
    public static double cov(double[] x, double[] y) {
        return dot_dev(x, y) / (x.length - 1);
    }

    /**
     * @param a 数値
     * @return 分散
     */
    public static double var(double[] a) {
        return devsq(a) / (a.length - 1);
    }

    /**
     * @return 標準偏差
     */
    public static double sd(double[] a) {
        return Math.sqrt(var(a));
    }

    /**
     * @return ピアソンの積率相関係数
     */
    public static double cor(double[] x, double[] y) {
        return cov(x, y) / (sd(x) * sd(y));
    }


    /**
     * 直線回帰
     *
     * @param x x:独立変数
     * @param y y:従属変数
     * @return y:=ax+bのモデル
     */
    public static double[] linearRegression(double[] x, double[] y) {
        if (x.length != y.length) return new double[]{0.0, 0.0};
        double a = dot_dev(x, y) / devsq(x);
        double c = cor(x, y);
        return new double[]{a, mean(y) - a * mean(x), c * c};
    }

    /**
     * @return 標準正規分布
     */
    public static double snorm() {
        return Math.sqrt(-2.0 * Math.log(Math.random())) * Math.cos(2.0 * Math.PI * Math.random());
    }

    /**
     * @return 正規分布
     */
    public static double norm(double norm_mean, double norm_var) {
        return norm_mean + norm_var * snorm();
    }

    /**
     * @param df 自由度
     * @return χ二乗分布
     */
    public static double chisq(int df) {
        double sum_chisq = 0.0;
        while (df-- > 0) {
            double sq = snorm();
            sum_chisq += sq * sq;
        }
        return sum_chisq;
    }

    /**
     * @param df 自由度
     * @return t分布
     */
    public static double rt(int df) {
        return snorm() / Math.sqrt(chisq(df) / df);
    }

    /**
     * @param df 自由度
     * @return F分布
     */
    public static double rf(int df, int df2) {
        return (chisq(df) / df) / (chisq(df2) / df2);
    }


    public static final double gxp_pi2 = 1 / Math.sqrt(2 * Math.PI);

    /**
     * @return 正規分布の上側確率
     */
    public static double gxp(double gxp_x) {
        double y = Math.abs(gxp_x), p = 0.0, c = y * y, z = Math.exp(-c * 0.5) * gxp_pi2;
        if (y < 2.5) {
            int is = -1;
            for (int i = 20; i > 0; i--) {
                p = c * i / (is * p + i * 2 + 1);
                is = -is;
            }
            p = 0.5 - z * y / (1.0 - p);
        } else {
            for (int i = 20; i > 0; i--) p = (double) i / (y + p);
            p = z / (y + p);
        }
        return (gxp_x < 0.0) ? 1.0 - p : p;
    }


    /**
     * @return χ二乗分布の上側確率
     */
    public static double xxp(double xxp_x, int df) {
        if (xxp_x == 0.0) {
            return 1.0;
        } else if (df == 1) {
            return gxp(Math.sqrt(xxp_x)) * 2.0;
        } else if (df == 2) {
            return Math.exp(-xxp_x / 2.0);
        } else if ((df % 2) == 0) {
            double w = 1.0, t = 1.0;
            int n = (df - 2) / 2;
            for (int i = 0; i < n; i++) {
                t *= xxp_x / ((1 + i) * 2);
                w += t;
            }
            return Math.exp(Math.log(w) - xxp_x * 0.5);
        } else {
            double w = Math.sqrt(xxp_x), t = w;
            int n = (df - 2) / 2;
            for (int i = 0; i < n; i++) {
                t *= xxp_x / ((1 + i) * 2 + 1);
                w += t;
            }
            return 2.0 * (gxp(Math.sqrt(xxp_x)) + gxp_pi2 * Math.exp(Math.log(w) - xxp_x * 0.5));
        }
    }

    /**
     * @return t分布の上側確率
     */
    public static double txp(double txp_x, int df) {
        double m2pi = 0.636619772367581343076;
        double t1 = Math.abs(txp_x) / Math.sqrt(df), t2 = 1.0 / (1.0 + t1 * t1);
        double p = 0.0;
        if ((df % 2) == 0) {
            double w = t1 * Math.sqrt(t2);
            p = 1.0 - w;
            int n = (df - 2) / 2;
            for (int i = 0; i < n; i++) {
                w *= t2 * ((1 + i) * 2 - 1) / ((1 + i) * 2);
                p -= w;
            }
        } else {
            p = 1.0 - m2pi * Math.atan2(t1, 1);
            if (df >= 3) {
                double w = m2pi * t1 * t2;
                p -= w;
                int n = (df - 2) / 2;
                for (int i = 0; i < n; i++) {
                    w *= t2 * ((1 + i) * 2 - 1) / ((1 + i) * 2 + 1);
                    p -= w;
                }
            }
        }
        return ((p < 0.0) && (Math.abs(p) < 0.000001)) ? 0.0 : p;
    }

    /**
     * @return F分布の上側確率
     */
    public static double fxp(double fxp_x, int df1, int df2) {
        double m2pi = 0.636619772367581343076;
        int i1 = 2 - (df1 % 2), i2 = 2 - (df2 % 2);
        double w = (double) df2 / (df2 + df1 * fxp_x), y = 1.0 - w,
                p = Math.sqrt(w),
                s = Math.sqrt(y), c;
        switch (2 * i1 - i2) {
            case 0:
                p = 1.0 - s;
                c = s * w / 2.0;
                break;
            case 1:
                c = s * p / Math.PI;
                p = 1.0 - Math.atan2(s, p) * m2pi;
                break;
            case 2:
                p = w;
                c = w * y;
                break;
            default:
                c = y * p / 2.0;
                break;
        }
        for (int i = i2; i < df2; i += 2) {
            p -= 2.0 / i * c;
            c *= w * (i1 + i) / i;
        }
        int i;
        for (i = i2; i < df2; i += 2) {
            p -= 2.0 / i * c;
            c *= w * (i1 + i) / i;
        }
        i2 = i;
        for (i = i1; i < df1; i += 2) {
            p += 2.0 / i * c;
            c *= y * (i + i2) / i;
        }
        return ((p < 0.0) & (Math.abs(p) < 0.000001)) ? 0.0 : p;
    }


}
