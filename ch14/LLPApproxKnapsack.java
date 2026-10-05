// LLP FPTAS for Knapsack: discard items that cannot fit, then run a profit-indexed
// min-weight DP D[i][p] on a lattice of size (nf+1) x (V'+1), V' = O(nf^2/eps),
// where nf is the number of feasible items.  Fully polynomial.
// D[i][p] = minimum total weight of a subset of the first i feasible items with
// scaled profit >= p; the answer is max { p : D[nf][p] <= W }, profit mu * p.

import java.util.*;

public class LLPApproxKnapsack {
  public int LLPApproxKnapsack(int[] w, int[] v, int W, int epsNum, int epsDen) {
    int n = w.length;
    int nf = 0;
    int i = 0;
    while ((i < n)) {
      if ((w[i] <= W)) {
        nf = (nf + 1);
      }
      i = (i + 1);
    }
    int[] fw = new int[nf];
    int[] fv = new int[nf];
    int k = 0;
    i = 0;
    while ((i < n)) {
      if ((w[i] <= W)) {
        fw[k] = w[i];
        fv[k] = v[i];
        k = (k + 1);
      }
      i = (i + 1);
    }
    int M = 1;
    if ((nf > 0)) {
      M = fv[0];
      i = 1;
      while ((i < nf)) {
        if ((fv[i] > M)) {
          M = fv[i];
        }
        i = (i + 1);
      }
    }
    int[] vPrime = new int[nf];
    int Vp = 0;
    i = 0;
    while ((i < nf)) {
      vPrime[i] = (((fv[i] * nf) * epsDen) / (epsNum * M));
      Vp = (Vp + vPrime[i]);
      i = (i + 1);
    }
    int INF = 1;
    i = 0;
    while ((i < nf)) {
      INF = (INF + fw[i]);
      i = (i + 1);
    }
    int[][] D = new int[(nf + 1)][(Vp + 1)];
    i = 0;
    while ((i <= nf)) {
      int p = 0;
      while ((p <= Vp)) {
        D[i][p] = INF;
        if ((p == 0)) {
          D[i][p] = 0;
        }
        p = (p + 1);
      }
      i = (i + 1);
    }
    boolean changed = true;
    while (changed) {
      changed = false;
      i = 1;
      while ((i <= nf)) {
        int p = 1;
        while ((p <= Vp)) {
          int target = D[(i - 1)][p];
          int prev = (p - vPrime[(i - 1)]);
          if ((prev < 0)) {
            prev = 0;
          }
          int take = (fw[(i - 1)] + D[(i - 1)][prev]);
          if ((take < target)) {
            target = take;
          }
          if ((target < D[i][p])) {
            D[i][p] = target;
            changed = true;
          }
          p = (p + 1);
        }
        i = (i + 1);
      }
    }
    int pStar = 0;
    int p = 0;
    while ((p <= Vp)) {
      if ((D[nf][p] <= W)) {
        pStar = p;
      }
      p = (p + 1);
    }
    return pStar;
  }

  public static void main(String[] args) {
    int[] w = new int[] {0, 0, 1, 2};
    int[] v = new int[] {0, 0, 1, 1};
    int W = 10;
    int epsNum = 1;
    int epsDen = 2;
    LLPApproxKnapsack prog = new LLPApproxKnapsack();
    int result = prog.LLPApproxKnapsack(w, v, W, epsNum, epsDen);
    System.out.println(result);
  }
}