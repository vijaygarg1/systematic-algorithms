// Dutch-flag partition: split A[low..high) into <, =, > pivot regions.

import java.util.*;

public class ThreeWayPartition {
  public int[] partition(int[] A, int pivot, int low, int high) {
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
    int[] result = prog.partition(A, pivot, 0, A.length);
    System.out.println(Arrays.toString(result));
  }
}