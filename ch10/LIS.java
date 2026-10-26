// Classical O(n²) LIS DP.

import java.util.*;

public class LIS {
  public int[] solve(int[] A) {
    int n = A.length;
    int[] dp = new int[n];
    for (int i = 0; i < n; i++) {
      dp[i] = 1;
    }
    for (int i = 1; i < n; i++) {
      for (int j = 0; j < i; j++) {
        if ((A[j] < A[i])) {
          dp[i] = Math.max(dp[i], (dp[j] + 1));
        }
      }
    }
    return dp;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    LIS prog = new LIS();
    int[] result = prog.solve(A);
    System.out.println(Arrays.toString(result));
  }
}