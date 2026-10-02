// Excluded-rooms constraint: room G[j] must not be in the excluded set R[j].

import java.util.*;

public class ExcludedRooms {
  int n;
  int[][] R;
  int[] G;
  int[][] pre;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int r : R[j]) {
      if ((G[j] == r)) { t1 = true; break; }
    }
    return t1;
  }

  private void advance(int j) {
    G[j] = leastValidRoom(j);
  }

  public void ExcludedRooms(int[][] R, int[] G, int[][] pre) {
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
            advance(j);
            changed = true;
          }
        }
      }
    }
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
    // Demo harness for ExcludedRooms.
    // Construct with hard-coded inputs and call the entry method.
  }
}