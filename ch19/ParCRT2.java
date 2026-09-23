// Par-CRT2: descending parallel Chinese Remainder Theorem.
// Searches for the largest solution below M.

import java.util.*;

public class ParCRT2 {
  public int[] ParCRT2(int[] m, int[] b, int M) {
    int[] G = new int[m.length];
    int j = 0;
    while ((j < m.length)) {
      int r = ((M - 1) % m[j]);
      if ((r >= b[j])) {
        G[j] = (((M - 1) - r) + b[j]);
      } else {
        G[j] = ((((M - 1) - r) + b[j]) - m[j]);
      }
      j = (j + 1);
    }
    boolean changed = true;
    while (changed) {
      changed = false;
      j = 0;
      while ((j < m.length)) {
        int minVal = G[0];
        int i = 1;
        while ((i < m.length)) {
          if ((G[i] < minVal)) {
            minVal = G[i];
          }
          i = (i + 1);
        }
        if ((G[j] > minVal)) {
          int diff = (G[j] - minVal);
          int steps = (((diff + m[j]) - 1) / m[j]);
          G[j] = (G[j] - (steps * m[j]));
          changed = true;
        }
        j = (j + 1);
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] m = new int[] {1, 4, 7};
    int[] b = new int[] {2, 3, 5, 8};
    int M = 0;
    ParCRT2 prog = new ParCRT2();
    int[] result = prog.ParCRT2(m, b, M);
    System.out.println(Arrays.toString(result));
  }
}