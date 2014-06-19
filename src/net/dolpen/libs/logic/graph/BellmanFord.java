package net.dolpen.libs.logic.graph;

import java.util.Arrays;

public class BellmanFord {
    /**
     * ベルマンフォード法で最短路探索する。
     * 一周の距離が負の閉路がなければ、距離が負の枝があってもよい
     *
     * @param adj {@link Adjacent#getAdjecentList}による隣接点集合
     * @param s   始点
     * @param g   終点
     * @return 最短距離
     */
    public static int bf(int[][][] adj, int s, int g) {
        int[] d = new int[adj.length];
        Arrays.fill(d, Integer.MAX_VALUE / 2);
        d[s] = 0;
        for (int i = 0; i < adj.length; i++) {
            boolean update = false;
            for (int j = 0; j < adj.length; j++) {
                for (int[] e : adj[j]) {
                    int nd = d[j] + e[1];
                    if (nd < d[e[0]]) {
                        d[e[0]] = nd;
                        update = true;
                        if (i == adj.length - 1) {
                            return Integer.MIN_VALUE;
                        }
                    }
                }
            }
            if (!update)
                break;
        }
        return d[g];
    }
}