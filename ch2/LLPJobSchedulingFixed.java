// LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed.

import java.util.*;

public class LLPJobSchedulingFixed {
  int n;
  int[] t;
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
    int t1 = t[j];
    for (int i : pre[j]) t1 = Math.max(t1, (G[i] + t[j]));
    G[j] = t1;
    fixed[j] = true;
  }

  public int[] LLPJobSchedulingFixed(int[] t, int[][] pre) {
    this.t = t;
    this.pre = pre;
    this.n = t.length;
    this.G = t.clone();
    this.fixed = new boolean[n];
    {
      // dependList (Algorithm LLP-Sequential): dep[r] holds
      // every index whose guard reads a cell written at r.
      int[] _dc = new int[n];
      for (int _k = 0; _k < n; _k++) {
        _dc[_k]++;
        for (int _r : pre[_k]) if (_r >= 0 && _r < n) _dc[_r]++;
      }
      int[][] _dep = new int[n][];
      for (int _r = 0; _r < n; _r++) _dep[_r] = new int[_dc[_r]];
      int[] _df = new int[n];
      for (int _k = 0; _k < n; _k++) {
        _dep[_k][_df[_k]++] = _k;
        for (int _r : pre[_k]) if (_r >= 0 && _r < n) _dep[_r][_df[_r]++] = _k;
      }
      int[] _q = new int[n + 1];
      boolean[] _inq = new boolean[n];
      int _qh = 0, _qt = 0;
      for (int j = 0; j < n; j++) {
        _q[_qt++] = j; _inq[j] = true;
      }
      while (_qh != _qt) {
        int j = _q[_qh]; _qh = _qh + 1 == n + 1 ? 0 : _qh + 1; _inq[j] = false;
        if (!forbidden(j)) continue;
        this.j = j; advance();
        for (int _x : _dep[j]) if (!_inq[_x]) {
          _inq[_x] = true; _q[_qt] = _x; _qt = _qt + 1 == n + 1 ? 0 : _qt + 1;
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