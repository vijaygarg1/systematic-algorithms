// FPTAS for Knapsack: scale values down by scale = eps*M/n, then run the standard 0/1 DP on the scaled instance.

import java.util.*;

public class FPTASKnapsack {
  public boolean[] FPTASKnapsack(int[] w, int[] v, int W, int epsNum, int epsDen) {
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
    int Vp = 0;
    i = 0;
    while ((i < n)) {
      Vp = (Vp + vPrime[i]);
      i = (i + 1);
    }
    int[][] dp = new int[(n + 1)][(W + 1)];
    i = 1;
    while ((i <= n)) {
      int c = 0;
      while ((c <= W)) {
        dp[i][c] = dp[(i - 1)][c];
        if ((w[(i - 1)] <= c)) {
          int take = (dp[(i - 1)][(c - w[(i - 1)])] + vPrime[(i - 1)]);
          if ((take > dp[i][c])) {
            dp[i][c] = take;
          }
        }
        c = (c + 1);
      }
      i = (i + 1);
    }
    boolean[] S = new boolean[n];
    int rem = W;
    i = n;
    while ((i > 0)) {
      if ((dp[i][rem] != dp[(i - 1)][rem])) {
        S[(i - 1)] = true;
        rem = (rem - w[(i - 1)]);
      }
      i = (i - 1);
    }
    return S;
  }

  public static void main(String[] args) {
    int[] w = new int[] {1, 4, 7};
    int[] v = new int[] {2, 3, 5, 8};
    int W = 10;
    int epsNum = 0;
    int epsDen = 0;
    FPTASKnapsack prog = new FPTASKnapsack();
    boolean[] result = prog.FPTASKnapsack(w, v, W, epsNum, epsDen);
    System.out.println(Arrays.toString(result));
  }
}