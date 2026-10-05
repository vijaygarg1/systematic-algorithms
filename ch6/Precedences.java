// Precedences: composition program enforcing precedence constraints
// pre(j) on job starts.  Each job j cannot start until every i in
// pre(j) has finished (start G[i] plus processing time t[i]).
// Composed onto LLP-MinMaxLateness via predicate conjunction:
// [ LLP-MinMaxLateness(t, d, G) && Precedences(pre, t, G) ].

import java.util.*;

public class Precedences {
  int n;
  int[][] pre;
  int[] t;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] < (G[i] + t[i]))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    int m = Integer.MIN_VALUE;
    for (int i : pre[j]) m = Math.max(m, (G[i] + t[i]));
    G[j] = m;
  }

  public int[] Precedences(int[][] pre, int[] t, int[] G) {
    this.pre = pre;
    this.t = t;
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
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: Precedences's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}