package net.dolpen.libs.logic.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 数値の英語表記
 */
public class EnglishNumber {
    static String[] tensplace = new String[]{
            "zero", "ten", "twenty", "thirty", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"
    };
    static String[] raw = new String[]{
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };
    static String[] names = new String[]{"billion", "million", "thousand"};
    static int[] bases = new int[]{1000000000, 1000000, 1000};

    /**
     * 3桁以下の英訳
     * @param x 10001未満の数値
     * @param and and を付けるかどうか
     * @param zero ゼロをzeroと出力するか
     * @return 英語表記
     */
    private static List<String> threeDigitToString(int x, boolean and, boolean zero) {
        List<String> sl = new ArrayList<String>();
        if (x >= 100) {
            sl.add(raw[x / 100]);
            sl.add("hundred");
        }
        int y = x % 100;
        if (y == 0 && !zero) return sl;
        if (and && !sl.isEmpty()) sl.add("and");
        if (y < raw.length) {
            sl.add(raw[y]);
        } else {
            sl.add(tensplace[y / 10] + ((y % 10 > 0) ? ("-" + raw[y % 10]) : ""));
        }
        return sl;
    }

    /**
     * intの範囲の数値を英語表記にします
     * @param k 数値
     * @return 英語表記
     */
    public static String intToString(int k) {
        List<String> sl = new ArrayList<String>();
        int i = k;
        for (int c = 0; c < 3; c++) {
            if (i / bases[c] > 0) {
                sl.addAll(threeDigitToString(i /bases[c], false, false));
                sl.add(names[c]);
            }
            i = i % bases[c];
        }
        sl.addAll(threeDigitToString(i, true, k == 0));

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String str : sl) {
            if (!first) sb.append(" ");
            sb.append(str);
            first = false;
        }
        return sb.toString();
    }
}
