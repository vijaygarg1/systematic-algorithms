// Recursive merge sort: split, sort halves, merge.

import java.util.*;

public class MergeSort {
  public void MergeSort(int[] A, int low, int high) {
    if ((low < high)) {
      int mid = ((low + high) / 2);
      MergeSort(A, low, mid);
      MergeSort(A, (mid + 1), high);
      Merge(A, low, mid, high);
    }
  }

  public void Merge(int[] A, int low, int mid, int high) {
    int[] B = new int[A.length];
    for (int k = low; k <= high; k++) {
      B[k] = A[k];
    }
    int i = low;
    int j = (mid + 1);
    int k = low;
    while (((i <= mid) && (j <= high))) {
      if ((B[i] <= B[j])) {
        A[k] = B[i];
        i = (i + 1);
      } else {
        A[k] = B[j];
        j = (j + 1);
      }
      k = (k + 1);
    }
    while ((i <= mid)) {
      A[k] = B[i];
      i = (i + 1);
      k = (k + 1);
    }
    while ((j <= high)) {
      A[k] = B[j];
      j = (j + 1);
      k = (k + 1);
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    MergeSort prog = new MergeSort();
    prog.MergeSort(A, 0, A.length - 1);
    System.out.println(Arrays.toString(A));
  }
}