// LLP-WeightedSetCover: primal-dual f-approximation for weighted set
// cover, matching bxx-approximate.tex Algorithm LLP-WeightedSetCover
// (fig:llp-wsc). G[x] is the price on element x; a set s is tight when
// its incident prices saturate w(s); an element is slack when it lies
// in no tight set; slackMin(x) further requires x to be lex-minimal
// among every slack element sharing a set with it. Advancing raises
// x's price by the smallest remaining budget among the sets containing
// it, tightening at least one of them.
//
// Written as an explicit scan-to-fixpoint rather than the native
// forbidden(x) => advance: ... construct: the compiler infers a
// single-index guard's loop bound from the first 1D array parameter's
// length (here w.length, the number of SETS), but x ranges over
// ELEMENTS -- a different count whenever sets and elements aren't
// equinumerous. This compiles to exactly the boolean-changed/
// while(changed) shape the native construct would generate, over the
// correct bound.

import java.util.*;

public class LLPWeightedSetCover {
  public boolean[] LLPWeightedSetCover(int[][] S, double[] w) {
    int m = w.length;
    int u = S[0].length;
    double[] G = new double[u];
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int x = 0; x < u; x++) {
        if (slackMin(x, S, w, G)) {
          G[x] = (G[x] + minRemainingBudget(x, S, w, G, m));
          changed = true;
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