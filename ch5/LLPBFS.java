// LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1.

import java.util.*;

public class LLPBFS {
  int n;
  int[][] pre;
  int[] G;

  private boolean forbidden(int j) {
    int t1 = Integer.MAX_VALUE;
    for (int i : pre[j]) {
      int v = (G[i] + 1);
      if (v < t1) t1 = v;
    }
    return (G[j] > t1);
  }

  private void advance(int j) {
    int m = Integer.MAX_VALUE;
    for (int i : pre[j]) m = Math.min(m, (G[i] + 1));
    G[j] = m;
  }

  public void LLPBFS(int[][] pre, int[] G) {
    this.pre = pre;
    this.G = G;
    this.n = pre.length;
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
  }

  public static void main(String[] args) {
    // Demo harness for LLPBFS.
    // Construct with hard-coded inputs and call the entry method.
  }
}