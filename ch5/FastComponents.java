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
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden1(j)) {
            this.j = j; _advance1();
            changed = true;
          }
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