// LCM1: ascending LLP to find least common multiple.
// Forbidden when G[j] < G[i]; advance jumps to next multiple of A[j] >= G[i].

import java.util.*;

public class LCM1 {
  int n;
  int[] A;
  int[] G;
  int j;
  int picked_i;

  private boolean forbidden(int j) {
    for (int i = 0; i < n; i++) {
      if ((G[j] < G[i])) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void advance() {
    int i = picked_i;
    G[j] = (G[j] + (((((G[i] - G[j]) + A[j]) - 1) / A[j]) * A[j]));
  }

  public int[] LCM1(int[] A) {
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
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LCM1 prog = new LCM1();
    int[] result = prog.LCM1(A);
    System.out.println(Arrays.toString(result));
  }
}