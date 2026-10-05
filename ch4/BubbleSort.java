// Bubble sort: repeated adjacent compare-and-swap until a clean pass.

import java.util.*;

public class BubbleSort {
  public void sort(int[] A) {
    boolean found = true;
    while (found) {
      found = false;
      int j = 0;
      while ((j < (A.length - 1))) {
        if ((A[j] > A[(j + 1)])) {
          found = true;
          { int tmp = A[j]; A[j] = A[(j + 1)]; A[(j + 1)] = tmp; }
        }
        j = (j + 1);
      }
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    BubbleSort prog = new BubbleSort();
    prog.sort(A);
    System.out.println(Arrays.toString(A));
  }
}