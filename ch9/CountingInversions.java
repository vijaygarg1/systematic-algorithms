// Count inversions in O(n log n) via merge sort.

import java.util.*;

public class CountingInversions {
  public int count(int[] A, int low, int high) {
    if ((low >= high)) {
      return 0;
    }
    int mid = ((low + high) / 2);
    int invLeft = count(A, low, mid);
    int invRight = count(A, (mid + 1), high);
    int invMerge = mergeAndCount(A, low, mid, high);
    return ((invLeft + invRight) + invMerge);
  }

  public int mergeAndCount(int[] A, int low, int mid, int high) {
    int[] B = new int[A.length];
    for (int k = low; k <= high; k++) {
      B[k] = A[k];
    }
    int i = low;
    int j = (mid + 1);
    int k = low;
    int inv = 0;
    while (((i <= mid) && (j <= high))) {
      if ((B[i] <= B[j])) {
        A[k] = B[i];
        i = (i + 1);
      } else {
        A[k] = B[j];
        j = (j + 1);
        inv = (inv + ((mid - i) + 1));
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
    return inv;
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    CountingInversions prog = new CountingInversions();
    int result = prog.count(A, 0, A.length);
    System.out.println(result);
  }
}