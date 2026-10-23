// LLP-Layering: G[j] is the length of the longest path from a source to j.
// The forbidden predicate is the negation of B_layer restricted to j's
// incoming edges, so no index is forbidden exactly when B_layer holds.
// A vertex with no predecessors is never forbidden and keeps its initial
// label 0.
//
// Layering is defined only on a directed ACYCLIC graph -- a cycle has no
// longest path -- so `setAcyclic(pre)` states that as a condition on valid
// input.  It is the precondition that makes the schedule available: on an
// acyclic graph every index can be settled once, in topological order, so
// that when j is advanced all of its predecessors already hold their final
// values.  That is the schedule the boolean `fixed` array implements in the
// hand-written ports, and it is what gives the O(n+m) of Section 5.5.
// Without the promise the program can only relax to a fixpoint, revisiting
// a vertex every time a predecessor deepens, which costs O(n*m).

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
    if (!(t1)) return false;
    return true;
  }

  private void advance() {
    int t2 = 0;
    for (int i : pre[j]) t2 = Math.max(t2, (G[i] + 1));
    G[j] = t2;
  }

  public int[] LLPLayering(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
    {
      // topological order over pre, declared acyclic
      int[] _indeg = new int[n];
      int[] _sc = new int[n];
      for (int _k = 0; _k < n; _k++) {
        for (int _i : pre[_k]) if (_i >= 0 && _i < n) { _indeg[_k]++; _sc[_i]++; }
      }
      int[][] _succ = new int[n][];
      for (int _i = 0; _i < n; _i++) _succ[_i] = new int[_sc[_i]];
      int[] _sf = new int[n];
      for (int _k = 0; _k < n; _k++) {
        for (int _i : pre[_k]) if (_i >= 0 && _i < n) _succ[_i][_sf[_i]++] = _k;
      }
      int[] _q = new int[n];
      int _qh = 0, _qt = 0, _seen = 0, _total = 0;
      for (int _k = 0; _k < n; _k++) {
        _total++; if (_indeg[_k] == 0) _q[_qt++] = _k;
      }
      while (_qh != _qt) {
        int j = _q[_qh++]; _seen++;
        if (forbidden(j)) { this.j = j; advance(); }
        for (int _x : _succ[j]) if (--_indeg[_x] == 0) _q[_qt++] = _x;
      }
      if (_seen != _total) throw new IllegalArgumentException(
          "pre has a cycle; setAcyclic promises it does not");
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