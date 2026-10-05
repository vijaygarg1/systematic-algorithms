// LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed.

import java.util.*;

public class LLP_JobScheduling_Fixed {
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

  public void LLP_JobScheduling_Fixed(int[] t, int[][] pre) {
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
    // No runnable example: LLP_JobScheduling_Fixed's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}