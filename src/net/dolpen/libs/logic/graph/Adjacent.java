package net.dolpen.libs.logic.graph;

/**
 * 三次元配列に関する隣接点集合を作る。
 * この処理によって得られる三次元配列<code>adj</code>について、
 * <code>adj.length</code>ノード数
 * <code>adj[i].length</code>とはi番目のノードの出次数
 * <code>adj[i][j]</code>とはi番目のノードから伸びるエッジのうちj番目であり
 * <code>adj[i][j][0]</code>が行き先ノード
 * <code>adj[i][j][1]</code>が対応する重み/距離(整数値)である
 * 実数値の重み/距離を扱いたければ、
 * <code>adj[i][j][1]</code>は真の値にアクセスするためのインデックスとして使うことができる。
 */
public class Adjacent {
    /**
     * 隣接点集合を作成する（カウンターエッジなし）
     *
     * @param n    ノード数
     * @param from エッジ始点
     * @param to   エッジ終点
     * @param w    エッジ重み
     * @return 隣接点集合
     */
    public static int[][][] getAdjecentList(int n, int[] from, int[] to, int[] w) {
        int[][][] e = new int[n][][];
        int[] d = new int[n];
        for (int f : from)
            d[f]++;
        for (int i = 0; i < n; i++)
            e[i] = new int[d[i]][2];
        for (int i = 0; i < from.length; i++) {
            --d[from[i]];
            e[from[i]][d[from[i]]][0] = to[i];
            e[from[i]][d[from[i]]][1] = w[i];
        }
        return e;
    }

    /**
     * 隣接点集合を作成する
     * 無向グラフのための逆向き枝を追加する
     *
     * @param n    ノード数
     * @param from エッジ始点
     * @param to   エッジ終点
     * @param w    エッジ重み
     * @return 隣接点集合
     */
    public static int[][][] getAdjecentListWithCounter(int n, int[] from, int[] to, int[] w) {
        int[][][] e = new int[n][][];
        int[] d = new int[n];
        for (int f : from)
            d[f]++;
        for (int t : to)
            d[t]++;
        for (int i = 0; i < n; i++)
            e[i] = new int[d[i]][2];
        for (int i = 0; i < from.length; i++) {
            --d[from[i]];
            e[from[i]][d[from[i]]][0] = to[i];
            e[from[i]][d[from[i]]][1] = w[i];
            --d[to[i]];
            e[to[i]][d[to[i]]][0] = from[i];
            e[to[i]][d[to[i]]][1] = w[i];
        }
        return e;
    }


    /**
     * 隣接点集合を作成する
     * 残余グラフのエッジ(初期キャパシティ0)を追加する。
     *
     * @param n    ノード数
     * @param from エッジ始点
     * @param to   エッジ終点
     * @param w    エッジ重み
     * @return 隣接点集合
     */
    public static int[][][] getAdjecentListWithCounterFlow(int n, int[] from, int[] to, int[] w) {
        int[][][] e = new int[n][][];
        int[] d = new int[n];
        for (int f : from)
            d[f]++;
        for (int t : to)
            d[t]++;
        for (int i = 0; i < n; i++)
            e[i] = new int[d[i]][3];
        for (int i = 0; i < from.length; i++) {
            --d[from[i]];
            --d[to[i]];
            e[from[i]][d[from[i]]][0] = to[i];
            e[from[i]][d[from[i]]][1] = w[i];
            e[from[i]][d[from[i]]][2] = d[to[i]]; // カウンターエッジ
            e[to[i]][d[to[i]]][0] = from[i];
            e[to[i]][d[to[i]]][1] = 0;
            e[to[i]][d[to[i]]][2] = d[from[i]]; // カウンターエッジ
        }
        return e;
    }


    /**
     * 隣接点集合を作成する
     * フローネットワークのためのコストや残余ネットワークを追加する
     * カウンター枝がadj[to][adj[from][to][3]]で参照可能
     *
     * @param n    ノード数
     * @param from エッジ始点
     * @param to   エッジ終点
     * @param w    エッジ重み
     * @return 隣接点集合
     */
    public static int[][][] getAdjecentListWithCounterCost(int n, int[] from, int[] to, int[] w, int[] c) {
        int[][][] e = new int[n][][];
        int[] d = new int[n];
        for (int f : from)
            d[f]++;
        for (int t : to)
            d[t]++;
        for (int i = 0; i < n; i++)
            e[i] = new int[d[i]][4];
        for (int i = 0; i < from.length; i++) {
            --d[from[i]];
            --d[to[i]];
            e[from[i]][d[from[i]]][0] = to[i];
            e[from[i]][d[from[i]]][1] = w[i];
            e[from[i]][d[from[i]]][2] = c[i];
            e[from[i]][d[from[i]]][3] = d[to[i]];

            e[to[i]][d[to[i]]][0] = from[i];
            e[to[i]][d[to[i]]][1] = 0;
            e[to[i]][d[to[i]]][2] = -c[i];
            e[to[i]][d[to[i]]][2] = d[from[i]];
        }
        return e;
    }
}
