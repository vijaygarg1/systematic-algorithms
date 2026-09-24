// LLP-Layering: each j advances once all predecessors are fixed.

import java.util.*;

public class LLPLayering {
  int n;
  int[][] pre;
  int[] G;
  boolean[] fixed;
  int j;

  private boolean forbidden(int j) {
    if (fixed[j]) return false;
    for (int i : pre[j]) if (!fixed[i]) return false;
    return true;
  }

  private void advance() {
    int m = 0;
    for (int i : pre[j]) m = Math.max(m, (G[i] + 1));
    G[j] = m;
    fixed[j] = true;
  }

  public int[] LLPLayering(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
    this.fixed = new boolean[n];
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
    // Demo harness for LLPLayering.
    // Construct with hard-coded inputs and call the entry method.
  }
}