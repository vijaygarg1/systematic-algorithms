// LLP form of Johnson reweighting: raise prices G[j] until every reduced edge weight is non-negative.
//
// The graph is a weighted adjacency list of PREDECESSORS: pre[j] holds the
// tails of the edges into j and the parallel pw[j] their weights, so the
// weight of (pre[j][t], j) is pw[j][t]. No n x n matrix is involved, so the
// whole instance is O(n + m). The matrix form cost Theta(n^2) to hold and
// read, which put a floor under any bound expressed in m.
//
// The book states O(mn) for this program (bxx-sp-llp-johnson.tex:38: "Each
// LLP iteration can be computed in O(m) time, so the graph is reweighted in
// O(mn) time"). The O(nm log n) of the chapter summary table is for the
// COMBINED all-pairs method -- this reweighting followed by Dijkstra from
// every source -- which this program does not do; its output is the price
// vector, as the algorithm box's KwOut says.

import java.util.*;

public class LLPJohnson {
  int n;
  int[][] pre;
  int[][] pw;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(reducedNegative(j))) return false;
    return true;
  }

  private void advance() {
    for (int k = 0; k < n; k++) {
      G[k] = raised(k);
    }
  }

  public int[] LLPJohnson(int[][] pre, int[][] pw) {
    this.pre = pre;
    this.pw = pw;
    this.n = pre.length;
    this.G = new int[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean reducedNegative(int j) {
    int t = 0;
    while ((t < pre[j].length)) {
      if ((G[j] < (G[pre[j][t]] - pw[j][t]))) {
        return true;
      }
      t = (t + 1);
    }
    return false;
  }

  public int raised(int k) {
    int best = G[k];
    int t = 0;
    while ((t < pre[k].length)) {
      int cand = (G[pre[k][t]] - pw[k][t]);
      if ((cand > best)) {
        best = cand;
      }
      t = (t + 1);
    }
    return best;
  }

  public static void main(String[] args) {
    int[][] pre = new int[][] {{}, {0}, {0, 1}, {0, 1, 2}, {0, 1, 2, 3}, {0, 1, 2, 3, 4}, {0, 1, 2, 3, 4, 5}, {0, 1, 2, 3, 4, 5, 6}};
    int[][] pw = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPJohnson prog = new LLPJohnson();
    int[] result = prog.LLPJohnson(pre, pw);
    System.out.println(Arrays.toString(result));
  }
}