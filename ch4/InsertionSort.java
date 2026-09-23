// Insertion sort: walk each new element down to its place.

import java.util.*;

public class InsertionSort {
  public void sort(int[] A) {
    int i = 1;
    while ((i < A.length)) {
      int j = (i - 1);
      boolean done = false;
      while (((j >= 0) && (!done))) {
        if ((A[j] <= A[(j + 1)])) {
          done = true;
        } else {
          { int tmp = A[j]; A[j] = A[(j + 1)]; A[(j + 1)] = tmp; }
          j = (j - 1);
        }
      }
      i = (i + 1);
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    InsertionSort prog = new InsertionSort();
    prog.sort(A);
    System.out.println(Arrays.toString(A));
  }
}