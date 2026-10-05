// LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1.
// style: fixpoint

import java.util.*;

public class LLPBFS {
  int n;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int t1 = Integer.MAX_VALUE;
    for (int i : pre[j]) {
      int v = (G[i] + 1);
      if (v < t1) t1 = v;
    }
    return (G[j] > t1);
  }

  private void advance() {
    int m = Integer.MAX_VALUE;
    for (int i : pre[j]) m = Math.min(m, (G[i] + 1));
    G[j] = m;
  }

  public void LLPBFS(int[][] pre, int[] G) {
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
    // No runnable example: LLPBFS's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}