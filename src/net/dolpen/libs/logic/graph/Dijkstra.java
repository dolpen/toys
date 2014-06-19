package net.dolpen.libs.logic.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class Dijkstra {
    /**
     * ダイクストラ法で最短路探索する。
     *
     * @param adj {@link Adjacent#getAdjecentListWithCounter}による隣接点集合
     * @param s   始点
     * @param g   終点
     * @return 最短距離
     */
    public static int dijkstra(int[][][] adj, int nodes, int s, int g) {
        final int[] d = new int[adj.length];
        Arrays.fill(d, Integer.MAX_VALUE / 2);
        // プライオリティキューをTreeSetで実装する
        // 探索済み到達距離の短い順にノードのインデックスを吐き出す
        TreeSet<Integer> ts = new TreeSet<Integer>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return (d[a] != d[b]) ? (d[a] - d[b]) : (a - b);
            }
        });
        // 始点の距離をゼロとし、探索対象に追加。
        d[s] = 0;
        ts.add(s);
        // 探索対象の最も近い点から、近傍の点の距離を更新する。
        // 距離が更新された近傍の点は、探索対象に追加される。
        while (!ts.isEmpty()) {
            int n = ts.pollFirst();
            for (int[] e : adj[n]) {
                int nd = d[n] + e[1];
                if (nd < d[e[0]]) {
                    ts.remove(e[0]);
                    d[e[0]] = nd;
                    ts.add(e[0]);
                }
            }
        }
        // 配列dは、始点から各ノードへの最短距離である。
        // 更新不可能になったところで、終点への最短距離を得る。
        return d[g];
    }

}