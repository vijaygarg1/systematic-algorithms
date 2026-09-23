// Odd-Even Sort: alternating odd- and even-indexed compare-swap passes.

import java.util.*;

public class OddEvenSort {
  public void sort(int[] A) {
    boolean found = true;
    while (found) {
      found = false;
      int j = 1;
      while ((j < (A.length - 1))) {
        if ((A[j] > A[(j + 1)])) {
          found = true;
          { int tmp = A[j]; A[j] = A[(j + 1)]; A[(j + 1)] = tmp; }
        }
        j = (j + 2);
      }
      j = 0;
      while ((j < (A.length - 1))) {
        if ((A[j] > A[(j + 1)])) {
          found = true;
          { int tmp = A[j]; A[j] = A[(j + 1)]; A[(j + 1)] = tmp; }
        }
        j = (j + 2);
      }
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    OddEvenSort prog = new OddEvenSort();
    prog.sort(A);
    System.out.println(Arrays.toString(A));
  }
}