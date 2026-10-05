// FPTAS for Knapsack: discard items that cannot fit, then scale profits by
// mu = eps*M/nf (M = max profit among feasible items) and run the standard 0/1 DP
// profit-indexed as D[i][p] = min weight to reach scaled profit >= p.  The table is
// O(nf * V') with V' = O(nf^2/eps).  Fully polynomial.

import java.util.*;

public class FPTASKnapsack {
  public boolean[] FPTASKnapsack(int[] w, int[] v, int W, int epsNum, int epsDen) {
    int n = w.length;
    boolean[] S = new boolean[n];
    int nf = 0;
    int i = 0;
    while ((i < n)) {
      if ((w[i] <= W)) {
        nf = (nf + 1);
      }
      i = (i + 1);
    }
    if ((nf == 0)) {
      return S;
    }
    int[] fw = new int[nf];
    int[] fv = new int[nf];
    int[] orig = new int[nf];
    int k = 0;
    i = 0;
    while ((i < n)) {
      if ((w[i] <= W)) {
        fw[k] = w[i];
        fv[k] = v[i];
        orig[k] = i;
        k = (k + 1);
      }
      i = (i + 1);
    }
    int M = fv[0];
    i = 1;
    while ((i < nf)) {
      if ((fv[i] > M)) {
        M = fv[i];
      }
      i = (i + 1);
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
    i = 1;
    while ((i <= nf)) {
      int p = 1;
      while ((p <= Vp)) {
        D[i][p] = D[(i - 1)][p];
        int prev = (p - vPrime[(i - 1)]);
        if ((prev < 0)) {
          prev = 0;
        }
        int take = (fw[(i - 1)] + D[(i - 1)][prev]);
        if ((take < D[i][p])) {
          D[i][p] = take;
        }
        p = (p + 1);
      }
      i = (i + 1);
    }
    int pStar = 0;
    int q = 0;
    while ((q <= Vp)) {
      if ((D[nf][q] <= W)) {
        pStar = q;
      }
      q = (q + 1);
    }
    int bp = pStar;
    i = nf;
    while ((i > 0)) {
      if ((D[i][bp] < D[(i - 1)][bp])) {
        S[orig[(i - 1)]] = true;
        int prev = (bp - vPrime[(i - 1)]);
        if ((prev < 0)) {
          prev = 0;
        }
        bp = prev;
      }
      i = (i - 1);
    }
    return S;
  }

  public static void main(String[] args) {
    int[] w = new int[] {0, 0, 1, 2};
    int[] v = new int[] {0, 0, 1, 1};
    int W = 10;
    int epsNum = 1;
    int epsDen = 2;
    FPTASKnapsack prog = new FPTASKnapsack();
    boolean[] result = prog.FPTASKnapsack(w, v, W, epsNum, epsDen);
    System.out.println(Arrays.toString(result));
  }
}