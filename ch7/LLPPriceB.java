// Price_b: find the minimum price vector using LLP.
// forbidden(j): exists predecessor i with p[j] < p[i] - w[i,j].
// advance: p[j] := max { p[i] - w[i,j] | i in pre(j) }.

import java.util.*;

public class LLPPriceB {
  int n;
  int[][] pre;
  int[][] w;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] < (G[i] - w[i][j]))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    G[j] = maxPricePred(j, pre, w, G);
  }

  public void LLPPriceB(int[][] pre, int[][] w) {
    this.pre = pre;
    this.w = w;
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
  }

  public int maxPricePred(int j, int[][] pre, int[][] w, int[] G) {
    int best = G[j];
    int k = 0;
    while ((k < pre[j].length)) {
      int i = pre[j][k];
      int val = (G[i] - w[i][j]);
      if ((val > best)) {
        best = val;
      }
      k = (k + 1);
    }
    return best;
  }

  public static void main(String[] args) {
    int[][] pre = new int[][] {{}, {0}, {0, 1}, {0, 1, 2}, {0, 1, 2, 3}, {0, 1, 2, 3, 4}, {0, 1, 2, 3, 4, 5}, {0, 1, 2, 3, 4, 5, 6}};
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPPriceB prog = new LLPPriceB();
    prog.LLPPriceB(pre, w);
    System.out.println(Arrays.deepToString(pre));
    System.out.println(Arrays.deepToString(w));
  }
}