// HopBound: composition program enforcing a hop budget on the
// shortest-path relaxation chain.  h[j] is the number of edges along
// the chain that established G[j]; the constraint is h[j] <= k.
// Composed onto LLP-Dijkstra via predicate conjunction:
// [ LLP-Dijkstra-Hops(s, w, G, h) && HopBound(h, k) ].

import java.util.*;

public class HopBound {
  int n;
  private static final int[] _NO_EARLY_EXIT = new int[0];
  int[] h;
  int k;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((h[j] > k))) return false;
    return true;
  }

  private int[] advance() {
    return null;
  }

  public int[] HopBound(int[] h, int k, int[] G) {
    this.h = h;
    this.k = k;
    this.G = G;
    this.n = h.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; int[] _r = advance(); if (_r != _NO_EARLY_EXIT) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] h = new int[] {0, 0, 1, 2};
    int k = 0;
    int[] G = new int[] {0, 0, 1, 1};
    HopBound prog = new HopBound();
    int[] result = prog.HopBound(h, k, G);
    System.out.println(Arrays.toString(result));
  }
}