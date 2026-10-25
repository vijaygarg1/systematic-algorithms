// Dutch-flag partition: split A[low..high) into <, =, > pivot regions.
// Matches bxx-sort.tex Algorithm ThreeWayPartition (fig:partition) line
// for line. The book returns the pair (p, q); LL has no tuple return, so
// the two boundary indices come back in a two-slot array instead -- the
// only difference between the two.

import java.util.*;

public class ThreeWayPartition {
  public int[] ThreeWayPartition(int[] A, int pivot, int low, int high) {
    int p = low;
    int q = low;
    int k = high;
    while ((q < k)) {
      if ((A[q] < pivot)) {
        { int tmp = A[p]; A[p] = A[q]; A[q] = tmp; }
        p = (p + 1);
        q = (q + 1);
      } else {
        if ((A[q] > pivot)) {
          k = (k - 1);
          { int tmp = A[q]; A[q] = A[k]; A[k] = tmp; }
        } else {
          q = (q + 1);
        }
      }
    }
    int[] result = new int[2];
    result[0] = p;
    result[1] = q;
    return result;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    int pivot = 5;
    ThreeWayPartition prog = new ThreeWayPartition();
    int[] result = prog.ThreeWayPartition(A, pivot, 0, A.length);
    System.out.println(Arrays.toString(result));
  }
}