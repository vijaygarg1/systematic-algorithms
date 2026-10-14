// FPTAS for Knapsack: discard items that cannot fit, then scale profits by
// mu = eps*M/nf (M = max profit among feasible items) and run the standard 0/1 DP
// profit-indexed as D[i][p] = min weight to reach scaled profit >= p.  The table is
// O(nf * V') with V' = O(nf^2/eps).  Fully polynomial.
//
// The preprocessing is written with aggregate expressions -- filter, map, max,
// sum -- to mirror Algorithm FPTAS-Knapsack in the book, which states it in
// set-builder form: F := {i : w_i <= W}, M := max_{i in F} v_i, and
// v'_i := floor(v_i / scale) for every i in F.  `orig` is exactly F, and it
// carries the *original* item indices, which is what lets the backtrack map a
// selection in the scaled instance back to an item of the input.

import java.util.*;

public class FPTASKnapsack {
  public boolean[] FPTASKnapsack(int[] w, int[] v, int W, int epsNum, int epsDen) {
    boolean[] S = new boolean[w.length];
    java.util.ArrayList<Integer> t1_acc = new java.util.ArrayList<>();
    for (int t = 0; t < w.length; t++) {
      if ((w[t] <= W)) t1_acc.add(t);
    }
    int[] t1 = new int[t1_acc.size()];
    for (int _i = 0; _i < t1.length; _i++) t1[_i] = t1_acc.get(_i);
    int[] orig = t1;
    int nf = orig.length;
    if ((nf == 0)) {
      return S;
    }
    java.util.ArrayList<Integer> t2_acc = new java.util.ArrayList<>();
    for (int k = 0; k < nf; k++) {
      t2_acc.add(w[orig[k]]);
    }
    int[] t2 = new int[t2_acc.size()];
    for (int _i = 0; _i < t2.length; _i++) t2[_i] = t2_acc.get(_i);
    int[] fw = t2;
    java.util.ArrayList<Integer> t3_acc = new java.util.ArrayList<>();
    for (int k = 0; k < nf; k++) {
      t3_acc.add(v[orig[k]]);
    }
    int[] t3 = new int[t3_acc.size()];
    for (int _i = 0; _i < t3.length; _i++) t3[_i] = t3_acc.get(_i);
    int[] fv = t3;
    int t4 = Integer.MIN_VALUE;
    for (int k = 0; k < nf; k++) {
      int _v = fv[k];
      if (_v > t4) t4 = _v;
    }
    int M = t4;
    java.util.ArrayList<Integer> t5_acc = new java.util.ArrayList<>();
    for (int k = 0; k < nf; k++) {
      t5_acc.add((((fv[k] * nf) * epsDen) / (epsNum * M)));
    }
    int[] t5 = new int[t5_acc.size()];
    for (int _i = 0; _i < t5.length; _i++) t5[_i] = t5_acc.get(_i);
    int[] vPrime = t5;
    int t6 = 0;
    for (int k = 0; k < nf; k++) {
      t6 += vPrime[k];
    }
    int Vp = t6;
    int t7 = 0;
    for (int k = 0; k < nf; k++) {
      t7 += fw[k];
    }
    int INF = (1 + t7);
    int[][] D = initTable(nf, Vp, INF);
    int i = 1;
    while ((i <= nf)) {
      int p = 1;
      while ((p <= Vp)) {
        D[i][p] = Math.min(D[(i - 1)][p], (fw[(i - 1)] + D[(i - 1)][Math.max(0, (p - vPrime[(i - 1)]))]));
        p = (p + 1);
      }
      i = (i + 1);
    }
    java.util.ArrayList<Integer> t8_acc = new java.util.ArrayList<>();
    for (int q = 0; q <= Vp; q++) {
      if ((D[nf][q] <= W)) t8_acc.add(q);
    }
    int[] t8 = new int[t8_acc.size()];
    for (int _i = 0; _i < t8.length; _i++) t8[_i] = t8_acc.get(_i);
    int[] reach = t8;
    int bp = reach[(reach.length - 1)];
    i = nf;
    while ((i > 0)) {
      if ((D[i][bp] < D[(i - 1)][bp])) {
        S[orig[(i - 1)]] = true;
        bp = Math.max(0, (bp - vPrime[(i - 1)]));
      }
      i = (i - 1);
    }
    return S;
  }

  public int[][] initTable(int nf, int Vp, int INF) {
    int[][] D = new int[(nf + 1)][(Vp + 1)];
    int i = 0;
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
    return D;
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