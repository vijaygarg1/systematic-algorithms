// Classical FPTAS for 0/1 Knapsack: scale the profits down by
// mu = eps*M/nf, then solve the scaled problem exactly by a profit-indexed
// DP. Rounding loses at most eps*OPT, and the scaled profit range is
// O(nf^2/eps), so the whole thing is polynomial in nf and 1/eps.
//
// D[i][p] = least total weight of a subset of the first i feasible items
// whose scaled profit is at least p, with D[i][0] = 0 and every other
// cell starting at INF. The answer is the largest p with D[nf][p] <= W;
// the traceback then walks the rows back, taking item i exactly when
// including it is what made row i cheaper than row i-1.

import java.util.*;

public class FPTASKnapsack {
  public boolean[] FPTASKnapsack(int[] w, int[] v, int W, int epsNum, int epsDen) {
    boolean[] S = new boolean[w.length];
    int[] orig = new int[w.length];
    int nf = 0;
    for (int t = 0; t < w.length; t++) {
      {
        if ((w[t] <= W)) {
          orig[nf] = t;
          nf = (nf + 1);
        }
      }
    }
    if ((nf == 0)) {
      return S;
    }
    int[] fw = new int[nf];
    int[] fv = new int[nf];
    int k = 0;
    while ((k < nf)) {
      fw[k] = w[orig[k]];
      fv[k] = v[orig[k]];
      k = (k + 1);
    }
    int t1 = Integer.MIN_VALUE;
    for (int j = 0; j < nf; j++) {
      int _v = fv[j];
      if (_v > t1) t1 = _v;
    }
    int M = t1;
    int[] vPrime = new int[nf];
    int Vp = 0;
    int INF = 1;
    k = 0;
    while ((k < nf)) {
      vPrime[k] = (((fv[k] * nf) * epsDen) / (epsNum * M));
      Vp = (Vp + vPrime[k]);
      INF = (INF + fw[k]);
      k = (k + 1);
    }
    int[][] D = new int[(nf + 1)][(Vp + 1)];
    for (int a = 0; a <= nf; a++) {
      for (int b = 1; b <= Vp; b++) {
        D[a][b] = INF;
      }
    }
    for (int i = 1; i <= nf; i++) {
      for (int p = 1; p <= Vp; p++) {
        D[i][p] = Math.min(D[(i - 1)][p], (fw[(i - 1)] + D[(i - 1)][Math.max(0, (p - vPrime[(i - 1)]))]));
      }
    }
    int bp = 0;
    for (int q = 0; q <= Vp; q++) {
      {
        if ((D[nf][q] <= W)) {
          bp = q;
        }
      }
    }
    int i = nf;
    while ((i > 0)) {
      if ((D[i][bp] < D[(i - 1)][bp])) {
        S[orig[(i - 1)]] = true;
        bp = Math.max(0, (bp - vPrime[(i - 1)]));
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