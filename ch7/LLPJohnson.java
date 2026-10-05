// LLP form of Johnson reweighting: raise prices G[j] until every reduced edge weight is non-negative.

import java.util.*;

public class LLPJohnson {
  int n;
  int[][] pre;
  int[][] w;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] < (G[i] - w[i][j]))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
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
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: LLPJohnson's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}