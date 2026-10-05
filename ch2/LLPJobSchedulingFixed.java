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

  public int[] LLPJobSchedulingFixed(int[] t, int[][] pre) {
    this.t = t;
    this.pre = pre;
    this.n = t.length;
    this.G = t.clone();
    this.fixed = new boolean[n];
    for (int k = 0; k < n; k++) {
      fixed[k] = (pre[k].length == 0);
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
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: LLPJobSchedulingFixed's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}