// Optimal BST: O(n³) DP over interval [i, j] picking the root r.

import java.util.*;

public class OptimalBinarySearchTree {
  public double[][] OptimalBinarySearchTree(double[] prob) {
    int n = prob.length;
    double[][] dp = new double[n][n];
    double[][] s = new double[n][n];
    for (int i = 0; i < n; i++) {
      {
        dp[i][i] = prob[i];
        s[i][i] = prob[i];
      }
    }
    for (int len = 1; len < n; len++) {
      for (int lo = 0; lo <= ((n - 1) - len); lo++) {
        {
          int hi = (lo + len);
          s[lo][hi] = (s[lo][(hi - 1)] + prob[hi]);
          dp[lo][hi] = Double.POSITIVE_INFINITY;
          for (int r = lo; r <= hi; r++) {
            {
              double left = 0.0;
              double right = 0.0;
              if ((r > lo)) {
                left = dp[lo][(r - 1)];
              }
              if ((r < hi)) {
                right = dp[(r + 1)][hi];
              }
              double cost = ((s[lo][hi] + left) + right);
              dp[lo][hi] = Math.min(dp[lo][hi], cost);
            }
          }
        }
      }
    }
    return dp;
  }

  public static void main(String[] args) {
    double[] prob = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    OptimalBinarySearchTree prog = new OptimalBinarySearchTree();
    double[][] result = prog.OptimalBinarySearchTree(prob);
    System.out.println(Arrays.deepToString(result));
  }
}