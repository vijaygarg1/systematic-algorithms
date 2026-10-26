// LLP form of Johnson reweighting: raise prices G[j] until every reduced edge weight is non-negative.

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