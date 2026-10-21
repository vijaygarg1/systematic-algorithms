// LLP-WeightedSetCover: primal-dual f-approximation for weighted set
// cover, matching bxx-approximate.tex Algorithm LLP-WeightedSetCover
// (fig:llp-wsc). G[x] is the price on element x; a set s is tight when
// its incident prices saturate w(s); an element is slack when it lies
// in no tight set; slackMin(x) further requires x to be lex-minimal
// among every slack element sharing a set with it. Advancing raises
// x's price by the smallest remaining budget among the sets containing
// it, tightening at least one of them.
//
// The guard names its own range, x in [0..u-1], because x ranges over
// ELEMENTS while the bound the compiler would otherwise infer comes from
// the first 1D array parameter (w.length, the number of SETS) -- a
// different count whenever sets and elements aren't equinumerous.
//
// S, w and G are program fields, so the aux methods read them directly
// rather than taking them as parameters, and each predicate is the
// quantifier it is defined as.
//
// PRECONDITION: every element must lie in at least one set, or no cover
// exists at all. The guard enforces it rather than assuming it, because
// an uncoverable element is slack forever -- it lies in no tight set, so
// nothing can ever make it non-slack -- and the fixpoint marks a sweep
// as progress whenever a rule fires, not when G actually moves. Without
// the covered(x) conjunct below, one such element makes this program
// raise its price forever and never terminate. With it, an uncoverable
// element simply keeps price 0 and the sets returned cover everything
// that can be covered.

import java.util.*;

public class LLPWeightedSetCover {
  int n;
  int[][] S;
  double[] w;
  int m;
  int u;
  double[] G;
  int x;

  private boolean _forbidden0(int x) {
    if (!(covered(x))) return false;
    if (!(slackMin(x))) return false;
    return true;
  }

  private void _advance0() {
    G[x] = (G[x] + minRemainingBudget(x));
  }

  public boolean[] LLPWeightedSetCover(int[][] S, double[] w) {
    this.S = S;
    this.w = w;
    this.n = w.length;
    this.m = w.length;
    this.u = S[0].length;
    this.G = new double[u];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int x = 0; x < u; x++) {
          if (_forbidden0(x)) {
            this.x = x; _advance0();
            changed = true;
          }
        }
      }
    }
    boolean[] C = new boolean[m];
    for (int s = 0; s < m; s++) {
      C[s] = setTight(s);
    }
    return C;
  }

  public boolean covered(int x) {
    boolean t1 = false;
    for (int s = 0; s < m; s++) {
      if ((S[s][x] == 1)) { t1 = true; break; }
    }
    return t1;
  }

  public double setPrice(int s) {
    double total = 0.0;
    for (int e = 0; e < u; e++) {
      {
        if ((S[s][e] == 1)) {
          total = (total + G[e]);
        }
      }
    }
    return total;
  }

  public boolean setTight(int s) {
    return (setPrice(s) >= w[s]);
  }

  public boolean isSlack(int e) {
    boolean t2 = false;
    for (int s = 0; s < m; s++) {
      if (((S[s][e] == 1) && setTight(s))) { t2 = true; break; }
    }
    return (!t2);
  }

  public double minRemainingBudget(int e) {
    double best = Double.POSITIVE_INFINITY;
    for (int s = 0; s < m; s++) {
      {
        if ((S[s][e] == 1)) {
          double budget = (w[s] - setPrice(s));
          if ((budget < best)) {
            best = budget;
          }
        }
      }
    }
    return best;
  }

  public boolean slackMin(int x) {
    boolean t3 = false;
    for (int y = 0; y < x; y++) {
      if ((isSlack(y) && shareSet(x, y))) { t3 = true; break; }
    }
    return (isSlack(x) && (!t3));
  }

  public boolean shareSet(int x, int y) {
    boolean t4 = false;
    for (int s = 0; s < m; s++) {
      if (((S[s][x] == 1) && (S[s][y] == 1))) { t4 = true; break; }
    }
    return t4;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPWeightedSetCover prog = new LLPWeightedSetCover();
    boolean[] result = prog.LLPWeightedSetCover(S, w);
    System.out.println(Arrays.toString(result));
  }
}