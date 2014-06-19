package net.dolpen.libs.logic.math;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Heap {
    int[] a;
    Map<Integer, Integer> p;
    int nex = 0;
    int size = 0;
    int len = 0;

    public Heap(int len) {
        this.len = this.size = len;
        a = new int[len];
        p = new HashMap<Integer, Integer>();
        Arrays.fill(a, -1);
    }

    private void put(int n, int x) {
        if (p.containsKey(x))
            return;
        a[n] = x;
        p.put(x, n);

    }

    private int delete(int n) {
        int x = a[n];
        a[n] = -1;
        p.remove(n);
        return x;
    }

    private void swap(int n1, int n2) {
        int x1 = a[n1], x2 = a[n2];
        a[n1] = x2;
        a[n2] = x1;
        p.put(x1, n2);
        p.put(x2, n1);
    }

    private int shiftUp(int n) {
        int r = n >> 1;
        while (n > 0 && a[r] < a[n]) {
            swap(r, n);
            n = r;
            r = n >> 1;
        }
        return n;
    }

    private int shiftDown(int n) {
        int r = ((n + 1) << 1) - 1;
        while (r < len) {
            if (r + 1 < len && a[r] < a[r + 1])
                r++;
            if (a[r] < a[n]) break;
            swap(r, n);
            n = r;
            r = ((n + 1) << 1) - 1;
        }
        return n;
    }

    public void push(int x) {
        put(nex, x);
        shiftUp(nex);
        for (int i = 0; i < len && a[nex] >= 0; i++) {
            nex++;
            if (nex >= len) nex = nex % len;
        }
    }

    public int poll() {
        int t = delete(0);
        put(0, -1);
        int n = shiftDown(0);
        delete(n);
        return t;
    }
}
