// Fast connected components: SlowComponents accelerated by a
// pointer-jumping clause G[j] >= G[G[j]], which doubles the effective
// reach of the label-propagation step at each round and brings the
// number of rounds down from O(diameter) to O(log diameter).

import java.util.*;

public class FastComponents {
  int n;
  int[][] adj;
  int[] G;
  int j;

  private boolean _forbidden0(int j) {
    if ((G[j] >= G[G[j]])) return false;
    return true;
  }

  private void _advance0() {
    G[j] = G[G[j]];
  }

  private boolean _forbidden1(int j) {
    int t1 = Integer.MIN_VALUE;
    for (int i : adj[j]) {
      int _v = G[i];
      if (_v > t1) t1 = _v;
    }
    return (!(G[j] >= t1));
  }

  private void _advance1() {
    int t2 = Integer.MIN_VALUE;
    for (int i : adj[j]) t2 = Math.max(t2, G[i]);
    G[j] = t2;
  }

  public int[] FastComponents(int[][] adj) {
    this.adj = adj;
    this.n = adj.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = i;
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
    {
      // dependList (Algorithm LLP-Sequential): dep[r] holds
      // every index whose guard reads a cell written at r.
      int[] _dc = new int[n];
      for (int _k = 0; _k < n; _k++) {
        _dc[_k]++;
        for (int _r : adj[_k]) if (_r >= 0 && _r < n) _dc[_r]++;
      }
      int[][] _dep = new int[n][];
      for (int _r = 0; _r < n; _r++) _dep[_r] = new int[_dc[_r]];
      int[] _df = new int[n];
      for (int _k = 0; _k < n; _k++) {
        _dep[_k][_df[_k]++] = _k;
        for (int _r : adj[_k]) if (_r >= 0 && _r < n) _dep[_r][_df[_r]++] = _k;
      }
      int[] _q = new int[n + 1];
      boolean[] _inq = new boolean[n];
      int _qh = 0, _qt = 0;
      for (int j = 0; j < n; j++) {
        _q[_qt++] = j; _inq[j] = true;
      }
      while (_qh != _qt) {
        int j = _q[_qh]; _qh = _qh + 1 == n + 1 ? 0 : _qh + 1; _inq[j] = false;
        if (!_forbidden1(j)) continue;
        this.j = j; _advance1();
        for (int _x : _dep[j]) if (!_inq[_x]) {
          _inq[_x] = true; _q[_qt] = _x; _qt = _qt + 1 == n + 1 ? 0 : _qt + 1;
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: FastComponents's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}