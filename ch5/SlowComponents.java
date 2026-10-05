// Connected components by ensure-clause label propagation.

import java.util.*;

public class SlowComponents {
  int n;
  int[][] adj;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int t1 = Integer.MIN_VALUE;
    for (int i : adj[j]) {
      int v = G[i];
      if (v > t1) t1 = v;
    }
    return (!(G[j] >= t1));
  }

  private void advance() {
    int m = Integer.MIN_VALUE;
    for (int i : adj[j]) m = Math.max(m, G[i]);
    G[j] = m;
  }

  public int[] SlowComponents(int[][] adj) {
    this.adj = adj;
    this.n = adj.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = i;
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
    // No runnable example: SlowComponents's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}