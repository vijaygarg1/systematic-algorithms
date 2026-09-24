// LLP-IntervalPartition: assign each course j to the least free room not used by any overlapping earlier course in pre[j]; advance fixes j once all of its pre-set is fixed.

import java.util.*;

public class LLPIntervalPartition {
  int n;
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
    G[j] = leastFreeRoom(j);
    fixed[j] = true;
  }

  public int[] LLPIntervalPartition(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = 1;
    this.fixed = new boolean[n];
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

  public int leastFreeRoom(int j) {
    int r = 1;
    boolean conflict = true;
    while (conflict) {
      conflict = false;
      for (int i : pre[j]) {
        if ((G[i] == r)) {
          conflict = true;
        }
      }
      if (conflict) {
        r = (r + 1);
      }
    }
    return r;
  }

  public static void main(String[] args) {
    // Demo harness for LLPIntervalPartition.
    // Construct with hard-coded inputs and call the entry method.
  }
}