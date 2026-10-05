// GCD2: ascending GCD via ceiling-ratio operations.
// Equivalent forbidden / advance form of the ensure-clause version
// G[j] >= max_i (A[j] * G[i] + A[i] - 1) / A[i].
//
// Forbidden picks any i for which the bound is currently violated;
// advance lifts G[j] to that single ratio.  Iterating over forbidden i's
// converges to the maximum because the ensure constraint is monotone.

import java.util.*;

public class GCD2 {
  int n;
  int[] A;
  int[] G;
  int j;
  int picked_i;

  private boolean _forbidden0(int j) {
    for (int i = 0; i < n; i++) {
      if (((G[j] * A[i]) < (A[j] * G[i]))) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void _advance0() {
    int i = picked_i;
    G[j] = ((((A[j] * G[i]) + A[i]) - 1) / A[i]);
  }

  public int[] GCD2(int[] A) {
    this.A = A;
    this.n = A.length;
    this.G = new int[n];
    for (int k = 0; k < n; k++) {
      G[k] = 1;
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    GCD2 prog = new GCD2();
    int[] result = prog.GCD2(A);
    System.out.println(Arrays.toString(result));
  }
}