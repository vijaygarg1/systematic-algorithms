// Count pairs (i, j) with i < j and A[i] + A[j] = target.

import java.util.*;

public class PairSum {
  public static int PairSum(int[] A, int target) {
    int count = 0;
    int i = 0;
    while ((i < A.length)) {
      int j = (i + 1);
      while ((j < A.length)) {
        if (((A[i] + A[j]) == target)) {
          count = (count + 1);
        }
        j = (j + 1);
      }
      i = (i + 1);
    }
    return count;
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    int target = 0;
    int result = PairSum(A, target);
    System.out.println(result);
  }
}