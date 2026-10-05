// GCD1: descending GCD via Euclidean reduction.
// Forbidden when G[j] > G[i]; advance reduces G[j] by mod.

import java.util.*;

public class GCD1 {
  int n;
  int[] A;
  int[] G;
  int j;
  int picked_i;

  private boolean forbidden(int j) {
    for (int i = 0; i < n; i++) {
      if ((G[j] > G[i])) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void advance() {
    int i = picked_i;
    if (((G[j] % G[i]) == 0)) {
      G[j] = G[i];
    } else {
      G[j] = (G[j] % G[i]);
    }
  }

  public int[] GCD1(int[] A) {
    this.A = A;
    this.n = A.length;
    this.G = A.clone();
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
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    GCD1 prog = new GCD1();
    int[] result = prog.GCD1(A);
    System.out.println(Arrays.toString(result));
  }
}