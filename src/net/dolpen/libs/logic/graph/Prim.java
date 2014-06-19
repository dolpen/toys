package net.dolpen.libs.logic.graph;

import java.util.Arrays;

public class Prim {
    /**
     * プリムのアルゴリズムで最小全域木を求める(O(n^2))
     * 本当はPriorityQueue使いたいけどクラス実装が面倒なのでここでは勘弁
     *
     * @param adj {@link Adjacent#getAdjecentListWithCounter}による隣接点集合
     * @return 最小全域木の総延長距離
     */
    public static int prim(int[][][] adj) {
        int[] c = new int[adj.length];
        Arrays.fill(c, Integer.MAX_VALUE);
        c[0] = 0;
        boolean[] vis = new boolean[adj.length];
        int w = 0;
        while (true) {
            int v = -1;
            for (int u = 0; u < adj.length; u++) {
                if (!vis[u] && (v == -1 || c[v] > c[u])) {
                    v = u;
                }
            }
            if (v == -1)
                break;
            w += c[v];
            vis[v] = true;
            for (int[] e : adj[v]) {
                if (!vis[e[0]] && c[e[0]] > e[1]) {
                    c[e[0]] = e[1];
                }
            }
        }
        return w;
    }

}