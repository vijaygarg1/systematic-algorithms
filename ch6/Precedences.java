// Precedence constraint: job j cannot start before all predecessors finish.

import java.util.*;

public class Precedences {
  int n;
  int[][] pre;
  int[] t;
  int[] G;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] < (G[i] + t[i]))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance(int j) {
    int m = Integer.MIN_VALUE;
    for (int i : pre[j]) m = Math.max(m, (G[i] + t[i]));
    G[j] = m;
  }

  public void Precedences(int[][] pre, int[] t, int[] G) {
    this.pre = pre;
    this.t = t;
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
    // Demo harness for Precedences.
    // Construct with hard-coded inputs and call the entry method.
  }
}