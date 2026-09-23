// LLP form of Johnson reweighting: raise prices G[j] until every reduced edge weight is non-negative.

import java.util.*;

public class LLPJohnson {
  int n;
  int[][] pre;
  int[][] w;
  int[] G;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] < (G[i] - w[i][j]))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance(int j) {
    for (int k = 0; k <= n; k++) {
      int m = G[k];
      for (int i : pre[k]) m = Math.max(m, (G[i] - w[i][k]));
      G[k] = m;
    }
  }

  public int[] LLPJohnson(int[][] pre, int[][] w) {
    this.pre = pre;
    this.w = w;
    this.n = pre.length;
    this.G = new int[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            advance(j);
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // Demo harness for LLPJohnson.
    // Construct with hard-coded inputs and call the entry method.
  }
}