// LLP-LIS. The book states B as two conjuncts, G[j] >= 1 and
// G[j] >= max{G[i]+1 | i in pre(j)}, and proves it lattice-linear; one
// `ensure` over their maximum says exactly that, so the forbidden
// predicate and the advance are read off B rather than restated.
// pre(j) = {i < j : A[i] < A[j]}, so priority(j) = j settles each index
// in one advance -- the schedule the book's `fixed` array records, an
// efficiency choice rather than part of the predicate.

import java.util.*;

public class LLPLIS {
  int n;
  int[] A;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int t1 = Integer.MIN_VALUE;
    for (int i : pre[j]) {
      int _v = (G[i] + 1);
      if (_v > t1) t1 = _v;
    }
    return (!(G[j] >= Math.max(1, t1)));
  }

  private void advance() {
    int t2 = 1;
    for (int i : pre[j]) t2 = Math.max(t2, (G[i] + 1));
    G[j] = t2;
  }

  public int[] LLPLIS(int[] A, int[][] pre) {
    this.A = A;
    this.pre = pre;
    this.n = A.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = 1;
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
    // No runnable example: LLPLIS's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}