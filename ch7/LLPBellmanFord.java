// LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation.
// The advance leaves G[0] alone -- the source distance is pinned at 0 -- and
// recomputes every other G[k] from its predecessors, as in the book's box.

import java.util.*;

public class LLPBellmanFord {
  int n;
  int[][] pre;
  int[][] w;
  double[] G;
  int j;

  private boolean _forbidden0(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] > (G[i] + w[i][j]))) { t1 = true; break; }
    }
    return t1;
  }

  private void _advance0() {
    for (int k = 1; k < n; k++) {
      double m = Double.POSITIVE_INFINITY;
      for (int i : pre[k]) m = Math.min(m, (G[i] + w[i][k]));
      G[k] = m;
    }
  }

  public double[] LLPBellmanFord(int[][] pre, int[][] w) {
    this.pre = pre;
    this.w = w;
    this.n = pre.length;
    this.G = new double[n];
    for (int i = 0; i < n; i++) this.G[i] = Double.POSITIVE_INFINITY;
    G[0] = 0;
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
    // No runnable example: LLPBellmanFord's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}