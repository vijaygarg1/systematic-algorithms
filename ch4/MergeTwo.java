// MergeTwo: merge two sorted arrays into one, matching bxx-sort.tex
// Algorithm MergeTwo (fig:mergetwo) line for line -- including the two
// leftover-copy loops, which the book also prints as a single line each.

import java.util.*;

public class MergeTwo {
  public int[] merge(int[] B, int[] C) {
    boolean t1 = true;
    for (int i = 0; i <= (B.length - 2); i++) {
      if (!((B[i] <= B[(i + 1)]))) { t1 = false; break; }
    }
    assert (t1);
    boolean t2 = true;
    for (int i = 0; i <= (C.length - 2); i++) {
      if (!((C[i] <= C[(i + 1)]))) { t2 = false; break; }
    }
    assert (t2);
    int m = B.length;
    int n = C.length;
    int[] D = new int[(m + n)];
    int i = 0;
    int j = 0;
    int k = 0;
    while (((i < m) && (j < n))) {
      if ((B[i] <= C[j])) {
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
    boolean t4 = true;
    for (int k$3 = 0; k$3 <= (D.length - 2); k$3++) {
      if (!((D[k$3] <= D[(k$3 + 1)]))) { t4 = false; break; }
    }
    assert (t4);
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