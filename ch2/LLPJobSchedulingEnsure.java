// LLP job scheduling, ensure-sugar form of the same recurrence.

import java.util.*;

public class LLPJobSchedulingEnsure {
  int n;
  int[] t;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int t1 = Integer.MIN_VALUE;
    for (int i : pre[j]) {
      int _v = (G[i] + t[j]);
      if (_v > t1) t1 = _v;
    }
    if (!((!(G[j] >= t1)))) return false;
    return true;
  }

  private void advance() {
    int t2 = Integer.MIN_VALUE;
    for (int i : pre[j]) t2 = Math.max(t2, (G[i] + t[j]));
    G[j] = t2;
  }

  public int[] LLPJobSchedulingEnsure(int[] t, int[][] pre) {
    this.t = t;
    this.pre = pre;
    this.n = t.length;
    this.G = t.clone();
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
    // No runnable example: LLPJobSchedulingEnsure's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}