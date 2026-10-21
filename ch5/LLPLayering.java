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
    int t2 = 0;
    for (int i : pre[j]) t2 = Math.max(t2, (G[i] + 1));
    G[j] = t2;
  }

  private double priority(int j) {
    return _q3(j);
  }

  public int[] LLPLayering(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
    while (true) {
      int _best = -1;
      double _bestKey = 0;
      for (int j = 0; j < n; j++) {
        if (!forbidden(j)) continue;
        double _k = priority(j);
        if (_best < 0 || _k < _bestKey) { _best = j; _bestKey = _k; }
      }
      if (_best < 0) break;
      int j = _best;
      this.j = j; advance();
    }
    return G;
  }

  private int _q3(int j) {
    int _acc = Integer.MIN_VALUE;
    for (int i : pre[j]) {
      int _v = (G[i] + 1);
      if (_v > _acc) _acc = _v;
    }
    return _acc;
  }

  public static void main(String[] args) {
    // No runnable example: LLPLayering's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}