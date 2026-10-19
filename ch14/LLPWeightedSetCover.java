// LLP-WeightedSetCover: primal-dual f-approximation for weighted set
// cover, matching bxx-approximate.tex Algorithm LLP-WeightedSetCover
// (fig:llp-wsc). G[x] is the price on element x; a set s is tight when
// its incident prices saturate w(s); an element is slack when it lies
// in no tight set; slackMin(x) further requires x to be lex-minimal
// among every slack element sharing a set with it. Advancing raises
// x's price by the smallest remaining budget among the sets containing
// it, tightening at least one of them.
//
// The guard names its own range, `x in [0..u-1]`, because x ranges over
// ELEMENTS while the bound the compiler would otherwise infer comes from
// the first 1D array parameter (w.length, the number of SETS) -- a
// different count whenever sets and elements aren't equinumerous.

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
    if (!(slackMin(x, S, w, G))) return false;
    return true;
  }

  private void _advance0() {
    G[x] = (G[x] + minRemainingBudget(x, S, w, G, m));
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
      C[s] = setTight(s, S, w, G);
    }
    return C;
  }

  public double setPrice(int s, int[][] S, double[] G) {
    double sum = 0.0;
    int e = 0;
    while ((e < G.length)) {
      if ((S[s][e] == 1)) {
        sum = (sum + G[e]);
      }
      e = (e + 1);
    }
    return sum;
  }

  public boolean setTight(int s, int[][] S, double[] w, double[] G) {
    return (setPrice(s, S, G) >= w[s]);
  }

  public boolean isSlack(int e, int[][] S, double[] w, double[] G, int m) {
    int s = 0;
    while ((s < m)) {
      if (((S[s][e] == 1) && setTight(s, S, w, G))) {
        return false;
      }
      s = (s + 1);
    }
    return true;
  }

  public double minRemainingBudget(int e, int[][] S, double[] w, double[] G, int m) {
    double best = Integer.MAX_VALUE;
    int s = 0;
    while ((s < m)) {
      if ((S[s][e] == 1)) {
        double slack = (w[s] - setPrice(s, S, G));
        if ((slack < best)) {
          best = slack;
        }
      }
      s = (s + 1);
    }
    return best;
  }

  public boolean slackMin(int x, int[][] S, double[] w, double[] G) {
    int m = w.length;
    int u = G.length;
    if ((!isSlack(x, S, w, G, m))) {
      return false;
    }
    int y = 0;
    while ((y < u)) {
      if ((((y != x) && isSlack(y, S, w, G, m)) && shareSet(x, y, S, m))) {
        if ((y < x)) {
          return false;
        }
      }
      y = (y + 1);
    }
    return true;
  }

  public boolean shareSet(int x, int y, int[][] S, int m) {
    int s = 0;
    while ((s < m)) {
      if (((S[s][x] == 1) && (S[s][y] == 1))) {
        return true;
      }
      s = (s + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPWeightedSetCover prog = new LLPWeightedSetCover();
    boolean[] result = prog.LLPWeightedSetCover(S, w);
    System.out.println(Arrays.toString(result));
  }
}