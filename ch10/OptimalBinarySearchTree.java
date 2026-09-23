// Optimal BST: O(n³) DP over interval [i, j] picking the root r.

import java.util.*;

public class OptimalBinarySearchTree {
  public double[][] solve(double[] prob) {
    int n = prob.length;
    double[][] dp = new double[n][n];
    double[][] s = new double[n][n];
    int i = 0;
    while ((i < n)) {
      dp[i][i] = prob[i];
      s[i][i] = prob[i];
      i = (i + 1);
    }
    int len = 1;
    while ((len < n)) {
      int lo = 0;
      while ((lo < (n - len))) {
        int hi = (lo + len);
        s[lo][hi] = (s[lo][(hi - 1)] + prob[hi]);
        double best = Integer.MAX_VALUE;
        int r = lo;
        while ((r <= hi)) {
          double left = 0.0;
          double right = 0.0;
          if ((r > lo)) {
            left = dp[lo][(r - 1)];
          }
          if ((r < hi)) {
            right = dp[(r + 1)][hi];
          }
          double cost = ((s[lo][hi] + left) + right);
          if ((cost < best)) {
            best = cost;
          }
          r = (r + 1);
        }
        dp[lo][hi] = best;
        lo = (lo + 1);
      }
      len = (len + 1);
    }
    return dp;
  }

  public static void main(String[] args) {
    double[] prob = new double[] {1.0, 2.0, 3.0, 4.0};
    OptimalBinarySearchTree prog = new OptimalBinarySearchTree();
    double[][] result = prog.solve(prob);
    System.out.println(Arrays.deepToString(result));
  }
}