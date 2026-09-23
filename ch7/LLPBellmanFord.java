// LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation.

import java.util.*;

public class LLPBellmanFord {
  int n;
  int[][] pre;
  int[][] w;
  int[] G;

  private boolean _forbidden0(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] > (G[i] + w[i][j]))) { t1 = true; break; }
    }
    return t1;
  }

  private void _advance0(int j) {
    for (int k = 0; k <= n; k++) {
      int m = G[k];
      for (int i : pre[k]) m = Math.min(m, (G[i] + w[i][k]));
      G[k] = m;
    }
  }

  public int[] LLPBellmanFord(int[][] pre, int[][] w) {
    this.pre = pre;
    this.w = w;
    this.n = pre.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = Integer.MAX_VALUE;
    G[0] = 0;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            _advance0(j);
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // Demo harness for LLPBellmanFord.
    // Construct with hard-coded inputs and call the entry method.
  }
}