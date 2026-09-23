// LLP-Edge-Relaxation: ensure-based shortest path via edge relaxation.

import java.util.*;

public class LLPEdgeRelaxation {
  int n;
  int s;
  int[][] pre;
  int[][] w;
  int[] G;
  int j;

  private boolean _forbidden0(int j) {
    int t1 = Integer.MAX_VALUE;
    for (int i : pre[j]) {
      int v = (G[i] + w[i][j]);
      if (v < t1) t1 = v;
    }
    return (!(G[j] <= t1));
  }

  private void _advance0() {
    int m = Integer.MAX_VALUE;
    for (int i : pre[j]) m = Math.min(m, (G[i] + w[i][j]));
    G[j] = m;
  }

  public void LLPEdgeRelaxation(int s, int[][] pre, int[][] w) {
    this.s = s;
    this.pre = pre;
    this.w = w;
    this.n = pre.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = Integer.MAX_VALUE;
    G[s] = 0;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    // Demo harness for LLPEdgeRelaxation.
    // Construct with hard-coded inputs and call the entry method.
  }
}