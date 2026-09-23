// LLP-WeightedSetCover: primal-dual f-approximation for weighted set cover.
// G[e] is the price on element e. A set s is tight when sum of prices >= w[s].
// forbidden: element e is slack (lies in no tight set).
// advance: raise G[e] by uniform step r.
// Note: this algorithm appears under \remove{} in the book.

import java.util.*;

public class LLPWeightedSetCover {
  public void LLPWeightedSetCover(int[][] S, double[] w) {
    int m = w.length;
    int u = S[0].length;
    double[] G = new double[u];
    boolean changed = true;
    while (changed) {
      changed = false;
      double r = computeStep(S, w, G, m, u);
      if ((r <= 0.0)) {
        changed = false;
      }
      int e = 0;
      while ((e < u)) {
        if (isSlack(e, S, w, G, m)) {
          G[e] = (G[e] + r);
          changed = true;
        }
        e = (e + 1);
      }
    }
  }

  public boolean setTight(int s, int[][] S, double[] w, double[] G) {
    double sum = 0.0;
    int e = 0;
    while ((e < G.length)) {
      if ((S[s][e] == 1)) {
        sum = (sum + G[e]);
      }
      e = (e + 1);
    }
    return (sum >= w[s]);
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

  public double computeStep(int[][] S, double[] w, double[] G, int m, int u) {
    double r = Integer.MAX_VALUE;
    int s = 0;
    while ((s < m)) {
      int slackCount = 0;
      int e = 0;
      while ((e < u)) {
        if (((S[s][e] == 1) && isSlack(e, S, w, G, m))) {
          slackCount = (slackCount + 1);
        }
        e = (e + 1);
      }
      if ((slackCount > 0)) {
        double sum = 0.0;
        e = 0;
        while ((e < u)) {
          if ((S[s][e] == 1)) {
            sum = (sum + G[e]);
          }
          e = (e + 1);
        }
        double ratio = ((w[s] - sum) / slackCount);
        if ((ratio < r)) {
          r = ratio;
        }
      }
      s = (s + 1);
    }
    return r;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0};
    LLPWeightedSetCover prog = new LLPWeightedSetCover();
    prog.LLPWeightedSetCover(S, w);
    System.out.println(Arrays.deepToString(S));
    System.out.println(Arrays.toString(w));
  }
}