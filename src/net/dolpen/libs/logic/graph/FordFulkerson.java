package net.dolpen.libs.logic.graph;

import java.util.ArrayDeque;
import java.util.Arrays;

public class FordFulkerson {
    /**
     * フォード・ファルカーソン（エドモンズ-カープアルゴリズム）のアルゴリズムで
     * ネットワークにおいて、始点から終点への最大流を求める
     *
     * @param adj {@link Adjacent#getAdjecentList}による隣接点集合
     * @param s   始点
     * @param g   終点
     * @return 最大流量
     */
    public static int ff(int[][][] adj, int s, int g) {
        boolean[] v = new boolean[adj.length];
        int f = 0;
        // 始点ノード,既に調べたそのノードからのエッジ次数
        ArrayDeque<int[]> ad = new ArrayDeque<int[]>();
        while (true) {
            Arrays.fill(v, false);
            ad.clear();
            v[s] = true;
            ad.addLast(new int[]{s, -1});
            while (!ad.isEmpty()) {
                int[] n = ad.pollLast();
                if (n[0] == g)
                    break;
                for (n[1]++; (n[1] < adj[n[0]].length && (v[adj[n[0]][n[1]][0]] || adj[n[0]][n[1]][1] <= 0)); n[1]++) ;
                if (n[1] >= adj[n[0]].length) {
                    continue;
                }
                ad.addLast(new int[]{n[0], n[1]});
                int nn = adj[n[0]][n[1]][0];
                v[nn] = true;
                ad.addLast(new int[]{nn, -1});
            }
            if (ad.isEmpty()) break;
            int[][] r = ad.toArray(new int[][]{});
            int min = Integer.MAX_VALUE / 2;
            for (int i = 0; i < r.length; i++) {
                min = Math.min(min, adj[r[i][0]][r[i][1]][1]);
            }
            if (min == 0) break;
            for (int i = 0; i < r.length; i++) {
                int nn = adj[r[i][0]][r[i][1]][0];
                int cn = adj[r[i][0]][r[i][1]][2];
                adj[nn][cn][1] += min;
                adj[r[i][0]][r[i][1]][1] -= min;
            }
            f += min;
        }
        return f;
    }
}