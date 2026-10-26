// LLP FPTAS for Knapsack, matching bxx-approximate.tex Algorithm
// LLP-ApproxKnapsack (fig:llp-approxknapsack): discard items that cannot
// fit, then run a profit-indexed min-weight DP D[i][p] on a lattice of
// size (nf+1) x (V'+1), V' = O(nf/eps), where nf is the number of
// feasible items. Fully polynomial.

import java.util.*;

public class LLPApproxKnapsack {
  int n;
  int[] w;
  int[] v;
  int W;
  int epsNum;
  int epsDen;
  int[] feas;
  int nf;
  int[] fw;
  int[] fv;
  int M;
  int L;
  int[] vPrime;
  int Vp;
  int INF;
  int[][] D;
  int i;
  int p;

  private boolean _forbidden0(int i, int p) {
    if (!((i >= 1))) return false;
    if (!((i <= nf))) return false;
    if (!((p >= 1))) return false;
    if (!((p <= Vp))) return false;
    if (!((D[i][p] > Math.min(D[(i - 1)][p], (fw[(i - 1)] + D[(i - 1)][Math.max(0, (p - vPrime[(i - 1)]))]))))) return false;
    return true;
  }

  private void _advance0() {
    D[i][p] = Math.min(D[(i - 1)][p], (fw[(i - 1)] + D[(i - 1)][Math.max(0, (p - vPrime[(i - 1)]))]));
  }

  public int LLPApproxKnapsack(int[] w, int[] v, int W, int epsNum, int epsDen) {
    this.w = w;
    this.v = v;
    this.W = W;
    this.epsNum = epsNum;
    this.epsDen = epsDen;
    this.n = w.length;
    this.feas = feasible(w, W);
    this.nf = feas.length;
    this.fw = gather(w, feas);
    this.fv = gather(v, feas);
    this.M = _q1(i, p);
    this.L = lowerBound(fw, fv, nf, W, M);
    this.vPrime = scaled(fv, nf, epsNum, epsDen, L);
    this.Vp = cappedAxis(vPrime, nf, epsNum, epsDen);
    this.INF = (1 + _q2(i, p));
    this.D = new int[(nf + 1)][(Vp + 1)];
    for (int a = 0; a <= nf; a++) {
      for (int b = 1; b <= Vp; b++) {
        D[a][b] = INF;
      }
    }
    n = (Math.max(nf, Vp) + 1);
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 0; i < n; i++) {
          for (int p = 0; p < n; p++) {
            if (_forbidden0(i, p)) {
              this.i = i; this.p = p; _advance0();
              changed = true;
            }
          }
        }
      }
    }
    return bestProfit();
  }

  public int bestProfit() {
    int best = 0;
    for (int q = 0; q <= Vp; q++) {
      {
        if ((D[nf][q] <= W)) {
          best = q;
        }
      }
    }
    return best;
  }

  public int[] feasible(int[] w, int W) {
    int[] idx = new int[w.length];
    int c = 0;
    for (int t = 0; t < w.length; t++) {
      {
        if ((w[t] <= W)) {
          idx[c] = t;
          c = (c + 1);
        }
      }
    }
    return java.util.Arrays.copyOfRange(idx, 0, Math.max(0, ((c - 1)) + 1));
  }

  public int[] gather(int[] a, int[] idx) {
    int[] out = new int[idx.length];
    for (int k = 0; k < idx.length; k++) {
      out[k] = a[idx[k]];
    }
    return out;
  }

  public int[] scaled(int[] fv, int nf, int epsNum, int epsDen, int L) {
    int[] out = new int[nf];
    for (int k = 0; k < nf; k++) {
      out[k] = (((fv[k] * nf) * epsDen) / (epsNum * L));
    }
    return out;
  }

  public int lowerBound(int[] fw, int[] fv, int nf, int W, int M) {
    boolean[] used = new boolean[nf];
    int room = W;
    int gval = 0;
    int picks = 0;
    while ((picks < nf)) {
      int cand = (0 - 1);
      int t = 0;
      while ((t < nf)) {
        if (((!used[t]) && (fw[t] <= room))) {
          if ((cand < 0)) {
            cand = t;
          } else {
            if (((fv[t] * fw[cand]) > (fv[cand] * fw[t]))) {
              cand = t;
            }
          }
        }
        t = (t + 1);
      }
      if ((cand < 0)) {
        picks = nf;
      } else {
        used[cand] = true;
        gval = (gval + fv[cand]);
        room = (room - fw[cand]);
        picks = (picks + 1);
      }
    }
    if ((M > gval)) {
      return M;
    }
    return gval;
  }

  public int cappedAxis(int[] vPrime, int nf, int epsNum, int epsDen) {
    int t3 = 0;
    for (int k = 0; k < nf; k++) {
      t3 += vPrime[k];
    }
    int total = t3;
    int capv = ((((2 * nf) * epsDen) / epsNum) + 1);
    if ((total < capv)) {
      return total;
    }
    return capv;
  }

  private int _q1(int i, int p) {
    int _acc = Integer.MIN_VALUE;
    for (int k = 0; k < nf; k++) {
      int _v = fv[k];
      if (_v > _acc) _acc = _v;
    }
    return _acc;
  }

  private int _q2(int i, int p) {
    int _acc = 0;
    for (int k = 0; k < nf; k++) {
      _acc += fw[k];
    }
    return _acc;
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