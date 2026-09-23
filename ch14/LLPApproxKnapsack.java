// LLP FPTAS for Knapsack: scaled-value DP on the 2D lattice G[i, c] driven by a forbidden / advance pair on (i, c) pairs.

import java.util.*;

public class LLPApproxKnapsack {
  public int[][] LLPApproxKnapsack(int[] w, int[] v, int W, int epsNum, int epsDen) {
    int n = w.length;
    int M = v[0];
    int i = 1;
    while ((i < n)) {
      if ((v[i] > M)) {
        M = v[i];
      }
      i = (i + 1);
    }
    int[] vPrime = new int[n];
    i = 0;
    while ((i < n)) {
      vPrime[i] = (((v[i] * n) * epsDen) / (epsNum * M));
      i = (i + 1);
    }
    int[][] G = new int[(n + 1)][(W + 1)];
    boolean changed = true;
    while (changed) {
      changed = false;
      i = 1;
      while ((i <= n)) {
        int c = 0;
        while ((c <= W)) {
          int target = G[(i - 1)][c];
          if ((w[(i - 1)] <= c)) {
            int take = (G[(i - 1)][(c - w[(i - 1)])] + vPrime[(i - 1)]);
            if ((take > target)) {
              target = take;
            }
          }
          if ((G[i][c] < target)) {
            G[i][c] = target;
            changed = true;
          }
          c = (c + 1);
        }
        i = (i + 1);
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] w = new int[] {1, 4, 7};
    int[] v = new int[] {2, 3, 5, 8};
    int W = 10;
    int epsNum = 0;
    int epsDen = 0;
    LLPApproxKnapsack prog = new LLPApproxKnapsack();
    int[][] result = prog.LLPApproxKnapsack(w, v, W, epsNum, epsDen);
    System.out.println(Arrays.deepToString(result));
  }
}