// LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed.

import java.util.*;

public class LLPJobSchedulingFixed {
  int n;
  int[] t;
  int[][] pre;
  int[] G;
  boolean[] fixed;
  int j;

  private boolean _forbidden0(int j) {
    if (fixed[j]) return false;
    for (int i : pre[j]) if (!fixed[i]) return false;
    return true;
  }

  private void _advance0() {
    int m = Integer.MIN_VALUE;
    for (int i : pre[j]) m = Math.max(m, (G[i] + t[j]));
    G[j] = m;
    fixed[j] = true;
  }

  public void LLPJobSchedulingFixed(int[] t, int[][] pre) {
    this.t = t;
    this.pre = pre;
    this.n = t.length;
    this.G = t.clone();
    this.fixed = new boolean[n];
    for (int k = 0; k < n; k++) {
      fixed[k] = false;
    }
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
    // Demo harness for LLPJobSchedulingFixed.
    // Construct with hard-coded inputs and call the entry method.
  }
}