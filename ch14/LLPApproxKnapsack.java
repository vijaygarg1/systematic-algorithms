// LLP FPTAS for Knapsack, matching bxx-approximate.tex Algorithm
// LLP-ApproxKnapsack (fig:llp-approxknapsack): discard items that cannot
// fit, then run a profit-indexed min-weight DP D[i][p] on a lattice of
// size (nf+1) x (V'+1), V' = O(nf^2/eps), where nf is the number of
// feasible items. Fully polynomial.
// D[i][p] = minimum total weight of a subset of the first i feasible
// items with scaled profit >= p; the answer is max { p : D[nf][p] <= W },
// profit mu * p.
//
// Forbidden(i,p) with i>=1,p>=1: D[i,p] > min(D[i-1,p], w_i + D[i-1,max(0,p-v_i')]);
// Advance: D[i,p] := that min -- realized below with the native
// forbidden(i,p) => advance: ... construct. The guard's shared bound n is
// set to max(nf,Vp)+1 (rather than nf+1 or Vp+1 individually, since the
// compiler infers one bound for every index of a guard) and the
// predicate explicitly re-restricts i to [1..nf] and p to [1..Vp], so
// out-of-range (i,p) pairs in the padded square scan are simply never
// forbidden. Every value the guard or its advance touches by bare name
// (D, fw, vPrime, nf, Vp) must be a class field for the auto-generated
// forbidden/advance helpers to see it, which this compiler only grants
// to a *leading, uninterrupted run* of declarations at the very top of
// the entry method -- hence the preprocessing (which genuinely needs
// loops: discarding items, scaling profits, summing) is factored into
// helper methods, so the entry method itself is nothing but one
// declaration per step, each a single call.

import java.util.*;

public class LLPApproxKnapsack {
  int n;
  int[] w;
  int[] v;
  int W;
  int epsNum;
  int epsDen;
  int nf;
  int[] fw;
  int[] fv;
  int M;
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
    this.nf = countFeasible(w, W);
    this.fw = feasibleWeights(w, W, nf);
    this.fv = feasibleValues(w, v, W, nf);
    this.M = maxProfit(fv, nf);
    this.vPrime = scaledProfits(fv, nf, M, epsNum, epsDen);
    this.Vp = sumVPrime(vPrime, nf);
    this.INF = totalWeightPlusOne(fw, nf);
    this.D = initTable(nf, Vp, INF);
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
    return readPStar(D, nf, Vp, W);
  }

  public int countFeasible(int[] w, int W) {
    int nf = 0;
    int i = 0;
    while ((i < w.length)) {
      if ((w[i] <= W)) {
        nf = (nf + 1);
      }
      i = (i + 1);
    }
    return nf;
  }

  public int[] feasibleWeights(int[] w, int W, int nf) {
    int[] fw = new int[nf];
    int k = 0;
    int i = 0;
    while ((i < w.length)) {
      if ((w[i] <= W)) {
        fw[k] = w[i];
        k = (k + 1);
      }
      i = (i + 1);
    }
    return fw;
  }

  public int[] feasibleValues(int[] w, int[] v, int W, int nf) {
    int[] fv = new int[nf];
    int k = 0;
    int i = 0;
    while ((i < w.length)) {
      if ((w[i] <= W)) {
        fv[k] = v[i];
        k = (k + 1);
      }
      i = (i + 1);
    }
    return fv;
  }

  public int maxProfit(int[] fv, int nf) {
    int M = 1;
    if ((nf > 0)) {
      M = fv[0];
      int i = 1;
      while ((i < nf)) {
        M = Math.max(M, fv[i]);
        i = (i + 1);
      }
    }
    return M;
  }

  public int[] scaledProfits(int[] fv, int nf, int M, int epsNum, int epsDen) {
    int[] vPrime = new int[nf];
    int i = 0;
    while ((i < nf)) {
      vPrime[i] = (((fv[i] * nf) * epsDen) / (epsNum * M));
      i = (i + 1);
    }
    return vPrime;
  }

  public int sumVPrime(int[] vPrime, int nf) {
    int Vp = 0;
    int i = 0;
    while ((i < nf)) {
      Vp = (Vp + vPrime[i]);
      i = (i + 1);
    }
    return Vp;
  }

  public int totalWeightPlusOne(int[] fw, int nf) {
    int INF = 1;
    int i = 0;
    while ((i < nf)) {
      INF = (INF + fw[i]);
      i = (i + 1);
    }
    return INF;
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

  public int readPStar(int[][] D, int nf, int Vp, int W) {
    int pStar = 0;
    int q = 0;
    while ((q <= Vp)) {
      if ((D[nf][q] <= W)) {
        pStar = q;
      }
      q = (q + 1);
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