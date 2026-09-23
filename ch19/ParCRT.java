// Par-CRT: ascending parallel Chinese Remainder Theorem.
// Forbidden when G[j] < G[i] for some i;
// advance jumps to next multiple-of-m[j] congruence >= G[i].

import java.util.*;

public class ParCRT {
  int n;
  int[] m;
  int[] b;
  int[] G;
  int picked_i;

  private boolean forbidden(int j) {
    for (int i = 0; i < n; i++) {
      if ((G[j] < G[i])) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void advance(int j) {
    int i = picked_i;
    G[j] = (G[j] + (((((G[i] - G[j]) + m[j]) - 1) / m[j]) * m[j]));
  }

  public int[] ParCRT(int[] m, int[] b) {
    this.m = m;
    this.b = b;
    this.n = m.length;
    this.G = b.clone();
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
    int[] m = new int[] {1, 4, 7};
    int[] b = new int[] {2, 3, 5, 8};
    ParCRT prog = new ParCRT();
    int[] result = prog.ParCRT(m, b);
    System.out.println(Arrays.toString(result));
  }
}