// Insertion sort: walk each new element down to its place.

import java.util.*;

public class InsertionSort {
  public void InsertionSort(int[] A) {
    int n = A.length;
    for (int i = 1; i < n; i++) {
      {
        int j = (i - 1);
        while ((j >= 0)) {
          if ((A[j] <= A[(j + 1)])) {
            break;
          }
          { int tmp = A[j]; A[j] = A[(j + 1)]; A[(j + 1)] = tmp; }
          j = (j - 1);
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    InsertionSort prog = new InsertionSort();
    prog.InsertionSort(A);
    System.out.println(Arrays.toString(A));
  }
}