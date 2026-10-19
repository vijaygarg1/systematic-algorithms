// LLP-Layering: G[j] is the length of the longest path from a source to j.
// The forbidden predicate is the negation of B_layer restricted to j's
// incoming edges, so no index is forbidden exactly when B_layer holds.
// A vertex with no predecessors is never forbidden and keeps its initial
// label 0.  Advancing j only once all of its predecessors have reached
// their final values -- priority(j) = max{G[i]+1 | i in pre(j)}, the
// schedule of Section 2.9 -- advances each vertex exactly once; that
// schedule is what the boolean `fixed` array implements in the
// hand-written ports, and it is an efficiency choice, not part of the
// predicate.

import java.util.*;

public class LLPLayering {
  int n;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] <= G[i])) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    int m = 0;
    for (int i : pre[j]) m = Math.max(m, (G[i] + 1));
    G[j] = m;
  }

  public int[] LLPLayering(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
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
    // No runnable example: LLPLayering's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}