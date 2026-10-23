// Reachability as a 1-bit LLP: forbidden = unreached vertex with reached predecessor.

import java.util.*;

public class LLPTraversal {
  int n;
  int[][] pre;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((!G[j]))) return false;
    boolean t1 = false;
    for (int i : pre[j]) {
      if (G[i]) { t1 = true; break; }
    }
    if (!(t1)) return false;
    return true;
  }

  private void advance() {
    G[j] = true;
  }

  public void LLPTraversal(int[][] pre, boolean[] G) {
    this.pre = pre;
    this.G = G;
    this.n = pre.length;
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
  }

  public static void main(String[] args) {
    // No runnable example: LLPTraversal's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}