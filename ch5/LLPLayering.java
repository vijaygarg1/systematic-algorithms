// LLP-Layering: each j advances once all predecessors are fixed.

import java.util.*;

public class LLPLayering {
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
    int m = 0;
    for (int i : pre[j]) m = Math.max(m, (G[i] + 1));
    G[j] = m;
    fixed[j] = true;
  }

  public int[] LLPLayering(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
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

  public static void main(String[] args) {
    // No runnable example: LLPLayering's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}