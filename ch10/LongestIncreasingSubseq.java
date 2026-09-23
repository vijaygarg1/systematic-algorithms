// Classical O(n²) LIS DP.

import java.util.*;

public class LongestIncreasingSubseq {
  public int[] solve(int[] A) {
    int n = A.length;
    int[] dp = new int[n];
    int i = 0;
    while ((i < n)) {
      dp[i] = 1;
      i = (i + 1);
    }
    i = 1;
    while ((i < n)) {
      int j = 0;
      while ((j < i)) {
        if ((A[j] < A[i])) {
          if (((dp[j] + 1) > dp[i])) {
            dp[i] = (dp[j] + 1);
          }
        }
        j = (j + 1);
      }
      i = (i + 1);
    }
    return dp;
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LongestIncreasingSubseq prog = new LongestIncreasingSubseq();
    int[] result = prog.solve(A);
    System.out.println(Arrays.toString(result));
  }
}