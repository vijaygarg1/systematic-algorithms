// LLP-JobScheduling-Fixed (bxx-llp-alg.tex, fig:llpJobSchedulingFixed):
// forbidden when job j is not yet fixed but every predecessor is;
// advance sets G[j] to the max completion time over predecessors plus
// t[j], then marks j fixed -- matching the book's forbidden/advance
// box exactly (not a plain Kahn's-style topological sweep, which
// drops the fixed/forbidden formulation entirely). See
// LLPJobSchedulingFixed.llp in this same directory for an identical
// algorithm under its own (no-underscore) class name.

import java.util.*;

public class LLP_JobScheduling_WithFixed {
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

  public int[] LLP_JobScheduling_WithFixed(int[] t, int[][] pre) {
    this.t = t;
    this.pre = pre;
    this.n = t.length;
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
    // No runnable example: LLP_JobScheduling_WithFixed's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}