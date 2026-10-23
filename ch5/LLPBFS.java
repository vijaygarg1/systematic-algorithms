// LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1.
// style: fixpoint

import java.util.*;

public class LLPBFS {
  int n;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int t1 = Integer.MAX_VALUE;
    for (int i : pre[j]) {
      int _v = (G[i] + 1);
      if (_v < t1) t1 = _v;
    }
    if (!((G[j] > t1))) return false;
    return true;
  }

  private void advance() {
    int t2 = Integer.MAX_VALUE;
    for (int i : pre[j]) t2 = Math.min(t2, (G[i] + 1));
    G[j] = t2;
  }

  public void LLPBFS(int[][] pre, int[] G) {
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
    // No runnable example: LLPBFS's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}