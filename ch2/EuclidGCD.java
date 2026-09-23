// LLP form of Euclid: forbidden when some G[i] < G[j]; advance subtracts.

import java.util.*;

public class EuclidGCD {
  int n;
  int[] A;
  int[] G;
  int picked_i;

  private boolean forbidden(int j) {
    for (int i = 1; i <= n; i++) {
      if ((G[j] > G[i])) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void advance(int j) {
    int i = picked_i;
    G[j] = (G[j] - G[i]);
  }

  public int[] EuclidGCD(int[] A) {
    this.A = A;
    this.n = A.length;
    this.G = A.clone();
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            advance(j);
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    EuclidGCD prog = new EuclidGCD();
    int[] result = prog.EuclidGCD(A);
    System.out.println(Arrays.toString(result));
  }
}