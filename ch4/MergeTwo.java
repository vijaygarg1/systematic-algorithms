// Merge two sorted arrays B and C into a new sorted D.

import java.util.*;

public class MergeTwo {
  public int[] merge(int[] B, int[] C) {
    int m = B.length;
    int n = C.length;
    int[] D = new int[(m + n)];
    int i = 0;
    int j = 0;
    int k = 0;
    while (((i < m) && (j < n))) {
      if ((B[i] < C[j])) {
        D[k] = B[i];
        i = (i + 1);
      } else {
        D[k] = C[j];
        j = (j + 1);
      }
      k = (k + 1);
    }
    while ((i < m)) {
      D[k] = B[i];
      i = (i + 1);
      k = (k + 1);
    }
    while ((j < n)) {
      D[k] = C[j];
      j = (j + 1);
      k = (k + 1);
    }
    return D;
  }

  public static void main(String[] args) {
    int[] B = new int[] {0, 0, 1, 2};
    int[] C = new int[] {0, 0, 1, 1};
    MergeTwo prog = new MergeTwo();
    int[] result = prog.merge(B, C);
    System.out.println(Arrays.toString(result));
  }
}