// ExcludedRooms: composition program enforcing that course j is not
// assigned a room from its excluded set R[j].  Composed onto
// LLP-IntervalPartition via predicate conjunction:
// [ LLP-IntervalPartition(s, f, G) && ExcludedRooms(R, G) ].

import java.util.*;

public class ExcludedRooms {
  int n;
  int[][] R;
  int[] G;
  int[][] pre;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int r : R[j]) {
      if ((G[j] == r)) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    G[j] = leastValidRoom(j);
  }

  public int[] ExcludedRooms(int[][] R, int[] G, int[][] pre) {
    this.R = R;
    this.G = G;
    this.pre = pre;
    this.n = R.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public int leastValidRoom(int j) {
    int r = 1;
    boolean conflict = true;
    while (conflict) {
      conflict = false;
      for (int x : R[j]) {
        if ((x == r)) {
          conflict = true;
        }
      }
      if ((!conflict)) {
        for (int k : pre[j]) {
          if ((G[k] == r)) {
            conflict = true;
          }
        }
      }
      if (conflict) {
        r = (r + 1);
      }
    }
    return r;
  }

  public static void main(String[] args) {
    // No runnable example: ExcludedRooms's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}