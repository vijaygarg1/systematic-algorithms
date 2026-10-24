// VertexImplications: if v is in the cover and (v,w) is an implication,
// then w must also be in the cover.
// forbidden(w): exists v with (v,w) in I and G[v]=1 and G[w]=0.
// advance: G[w] := 1.
//
// The implications arrive as reverse adjacency lists: inI[w] holds the v
// with (v,w) in I, which is exactly the set the forbidden condition
// quantifies over. A dense n x n matrix would make the guard scan all n
// candidates for every w, so a sweep would cost Theta(n^2) however few
// implications there are -- and reading the matrix is Theta(n^2) before
// the first guard is even evaluated.

import java.util.*;

public class VertexImplications {
  int n;
  int[][] inI;
  boolean[] G;
  int w;

  private boolean forbidden(int w) {
    boolean t1 = false;
    for (int v : inI[w]) {
      if ((G[v] && (!G[w]))) { t1 = true; break; }
    }
    if (!(t1)) return false;
    return true;
  }

  private void advance() {
    G[w] = true;
  }

  public void VertexImplications(int[][] inI, boolean[] G) {
    this.inI = inI;
    this.G = G;
    this.n = inI.length;
    {
      // dependList (Algorithm LLP-Sequential): dep[r] holds
      // every index whose guard reads a cell written at r.
      int[] _dc = new int[n];
      for (int _k = 0; _k < n; _k++) {
        for (int _r : inI[_k]) if (_r >= 0 && _r < n) _dc[_r]++;
        _dc[_k]++;
      }
      int[][] _dep = new int[n][];
      for (int _r = 0; _r < n; _r++) _dep[_r] = new int[_dc[_r]];
      int[] _df = new int[n];
      for (int _k = 0; _k < n; _k++) {
        for (int _r : inI[_k]) if (_r >= 0 && _r < n) _dep[_r][_df[_r]++] = _k;
        _dep[_k][_df[_k]++] = _k;
      }
      int[] _q = new int[n + 1];
      boolean[] _inq = new boolean[n];
      int _qh = 0, _qt = 0;
      for (int w = 0; w < n; w++) {
        _q[_qt++] = w; _inq[w] = true;
      }
      while (_qh != _qt) {
        int w = _q[_qh]; _qh = _qh + 1 == n + 1 ? 0 : _qh + 1; _inq[w] = false;
        if (!forbidden(w)) continue;
        this.w = w; advance();
        for (int _x : _dep[w]) if (!_inq[_x]) {
          _inq[_x] = true; _q[_qt] = _x; _qt = _qt + 1 == n + 1 ? 0 : _qt + 1;
        }
      }
    }
  }

  public static void main(String[] args) {
    // No runnable example: VertexImplications's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}