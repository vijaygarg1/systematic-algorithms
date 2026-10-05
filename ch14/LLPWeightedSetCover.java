// LLP-WeightedSetCover: primal-dual f-approximation for weighted set
// cover, matching bxx-approximate.tex Algorithm LLP-WeightedSetCover.
// G[x] is the price on element x; a set s is tight when its incident
// prices saturate w(s); an element is slack when it lies in no tight
// set. To keep this a well-formed LLP computation, only a lex-minimal
// slack element (one no larger, in element order, than any slack
// element sharing a set with it) may advance -- distinct lex-minimal
// slack elements never share a set, so advancing one never disturbs
// another. Each advance raises its element's price by the smallest
// remaining budget among the sets containing it, tightening at least
// one of them. Sequentially, repeatedly advancing the single globally
// lex-least slack element is a valid schedule. Output: C := tight sets.
//
// Earlier version of this file (kept for reference, not used): raised
// every currently-slack element by one common uniform step per round
// (Vazirani-style), rather than the book's per-element, lex-minimal-only
// step; also never returned the cover. It also carried a comment
// claiming this algorithm is under \remove{} in the book -- it is not,
// it is live (bxx-approximate.tex, \SetAlgoRefName{LLP-WeightedSetCover}).
//
// class LLPWeightedSetCover {
// void LLPWeightedSetCover(int[][] S, double[] w) {
// int m = w.length;
// int u = S[0].length;
// double[] G = new double[u];
// boolean changed = true;
// while (changed) {
// changed = false;
// double r = computeStep(S, w, G, m, u);
// if (r <= 0.0) { changed = false; };
// int e = 0;
// while (e < u) {
// if (isSlack(e, S, w, G, m)) {
// G[e] = G[e] + r;
// changed = true;
// };
// e = e + 1;
// }
// };
// }
//
// boolean setTight(int s, int[][] S, double[] w, double[] G) {
// double sum = 0.0;
// int e = 0;
// while (e < G.length) {
// if (S[s][e] == 1) { sum = sum + G[e]; };
// e = e + 1;
// };
// return sum >= w[s];
// }
//
// boolean isSlack(int e, int[][] S, double[] w, double[] G, int m) {
// int s = 0;
// while (s < m) {
// if (S[s][e] == 1 && setTight(s, S, w, G)) { return false; };
// s = s + 1;
// };
// return true;
// }
//
// double computeStep(int[][] S, double[] w, double[] G, int m, int u) {
// double r = infinity;
// int s = 0;
// while (s < m) {
// int slackCount = 0;
// int e = 0;
// while (e < u) {
// if (S[s][e] == 1 && isSlack(e, S, w, G, m)) {
// slackCount = slackCount + 1;
// };
// e = e + 1;
// };
// if (slackCount > 0) {
// double sum = 0.0;
// e = 0;
// while (e < u) {
// if (S[s][e] == 1) { sum = sum + G[e]; };
// e = e + 1;
// };
// double ratio = (w[s] - sum) / slackCount;
// if (ratio < r) { r = ratio; };
// };
// s = s + 1;
// };
// return r;
// }
// }

import java.util.*;

public class LLPWeightedSetCover {
  public boolean[] LLPWeightedSetCover(int[][] S, double[] w) {
    int m = w.length;
    int u = S[0].length;
    double[] G = new double[u];
    boolean done = false;
    while ((!done)) {
      int sx = (0 - 1);
      int e = 0;
      while (((e < u) && (sx == (0 - 1)))) {
        if (isSlack(e, S, w, G, m)) {
          sx = e;
        }
        e = (e + 1);
      }
      if ((sx == (0 - 1))) {
        done = true;
      } else {
        double step = minRemainingBudget(sx, S, w, G, m);
        G[sx] = (G[sx] + step);
      }
    }
    boolean[] C = new boolean[m];
    int s = 0;
    while ((s < m)) {
      C[s] = setTight(s, S, w, G);
      s = (s + 1);
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

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPWeightedSetCover prog = new LLPWeightedSetCover();
    boolean[] result = prog.LLPWeightedSetCover(S, w);
    System.out.println(Arrays.toString(result));
  }
}