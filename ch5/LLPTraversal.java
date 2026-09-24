// Reachability as a 1-bit LLP: forbidden = unreached vertex with reached predecessor.

import java.util.*;

public class LLPTraversal {
  int n;
  int[][] pre;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if (G[i]) { t1 = true; break; }
    }
    return ((!G[j]) && t1);
  }

  private void advance() {
    G[j] = true;
  }

  public void LLPTraversal(int[][] pre, boolean[] G) {
    this.pre = pre;
    this.G = G;
    this.n = pre.length;
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
  }

  public static void main(String[] args) {
    // Demo harness for LLPTraversal.
    // Construct with hard-coded inputs and call the entry method.
  }
}