// LLP job scheduling: forbidden when G[j] < max_{i in pre[j]} G[i] + t[j].

import java.util.*;

public class LLP_JobScheduling_Forbidden {
  int n;
  int[] t;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int t1 = Integer.MIN_VALUE;
    for (int i : pre[j]) {
      int v = (G[i] + t[j]);
      if (v > t1) t1 = v;
    }
    return (G[j] < t1);
  }

  private void advance() {
    int m = Integer.MIN_VALUE;
    for (int i : pre[j]) m = Math.max(m, (G[i] + t[j]));
    G[j] = m;
  }

  public int[] LLP_JobScheduling_Forbidden(int[] t, int[][] pre) {
    this.t = t;
    this.pre = pre;
    this.n = t.length;
    this.G = t.clone();
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
    // No runnable example: LLP_JobScheduling_Forbidden's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}