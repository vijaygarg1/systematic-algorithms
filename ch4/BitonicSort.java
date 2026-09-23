// Bitonic sorting network: ascending half + descending half + merge.

import java.util.*;

public class BitonicSort {
  public void sort(int[] A, int low, int n, int dir) {
    if ((n > 1)) {
      int m = (n / 2);
      /* [ ... || ... ]: independent branches (sequential for now) */
      // branch 0
      sort(A, low, m, 1);
      // branch 1
      sort(A, (low + m), m, 0);
      bitonicMerge(A, low, n, dir);
    }
  }

  public void bitonicMerge(int[] A, int low, int n, int dir) {
    if ((n > 1)) {
      int m = (n / 2);
      int i = low;
      while ((i < (low + m))) {
        int j = (i + m);
        if (((dir == 1) && (A[i] > A[j]))) {
          { int tmp = A[i]; A[i] = A[j]; A[j] = tmp; }
        }
        if (((dir == 0) && (A[i] < A[j]))) {
          { int tmp = A[i]; A[i] = A[j]; A[j] = tmp; }
        }
        i = (i + 1);
      }
      /* [ ... || ... ]: independent branches (sequential for now) */
      // branch 0
      bitonicMerge(A, low, m, dir);
      // branch 1
      bitonicMerge(A, (low + m), m, dir);
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    BitonicSort prog = new BitonicSort();
    prog.sort(A, 0, A.length, 1);
    System.out.println(Arrays.toString(A));
  }
}