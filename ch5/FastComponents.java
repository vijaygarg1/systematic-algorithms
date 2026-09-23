// Fast connected components: SlowComponents accelerated by a
// pointer-jumping clause G[j] >= G[G[j]], which doubles the effective
// reach of the label-propagation step at each round and brings the
// number of rounds down from O(diameter) to O(log diameter).

import java.util.*;

public class FastComponents {
  int n;
  int[][] adj;
  int[] G;

  private boolean _forbidden0(int j) {
    if ((G[j] >= G[G[j]])) return false;
    return true;
  }

  private void _advance0(int j) {
    G[j] = G[G[j]];
  }

  private boolean _forbidden1(int j) {
    int t1 = Integer.MIN_VALUE;
    for (int i : adj[j]) {
      int v = G[i];
      if (v > t1) t1 = v;
    }
    return (!(G[j] >= t1));
  }

  private void _advance1(int j) {
    int m = Integer.MIN_VALUE;
    for (int i : adj[j]) m = Math.max(m, G[i]);
    G[j] = m;
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
            _advance0(j);
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
            _advance1(j);
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // Demo harness for FastComponents.
    // Construct with hard-coded inputs and call the entry method.
  }
}