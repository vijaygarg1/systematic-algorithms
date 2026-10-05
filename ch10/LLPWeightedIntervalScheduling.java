// LLP weighted interval scheduling: G[j] >= max(G[j-1], w[j] + G[p[j]]).

import java.util.*;

public class LLPWeightedIntervalScheduling {
  int n;
  int[] w;
  int[] p;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((j >= 1))) return false;
    if (!((G[j] < maxRhs(j)))) return false;
    return true;
  }

  private void advance() {
    G[j] = maxRhs(j);
  }

  public int[] LLPWeightedIntervalScheduling(int[] w, int[] p) {
    this.w = w;
    this.p = p;
    this.n = w.length;
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

  public int maxRhs(int j) {
    int skip = G[(j - 1)];
    int take = (w[j] + G[p[j]]);
    if ((take > skip)) {
      return take;
    } else {
      return skip;
    }
  }

  public static void main(String[] args) {
    int[] w = new int[] {0, 0, 1, 2};
    int[] p = new int[] {0, 0, 1, 1};
    LLPWeightedIntervalScheduling prog = new LLPWeightedIntervalScheduling();
    int[] result = prog.LLPWeightedIntervalScheduling(w, p);
    System.out.println(Arrays.toString(result));
  }
}