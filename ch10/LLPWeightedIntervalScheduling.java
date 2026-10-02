// LLP weighted interval scheduling: G[j] >= max(G[j-1], w[j] + G[p[j]]).

import java.util.*;

public class LLPWeightedIntervalScheduling {
  int n;
  int[] w;
  int[] p;
  int[] G;

  private boolean forbidden(int j) {
    if (!((j >= 1))) return false;
    if (!((G[j] < maxRhs(j)))) return false;
    return true;
  }

  private void advance(int j) {
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
            advance(j);
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
    int[] w = new int[] {1, 4, 7};
    int[] p = new int[] {2, 3, 5, 8};
    LLPWeightedIntervalScheduling prog = new LLPWeightedIntervalScheduling();
    int[] result = prog.LLPWeightedIntervalScheduling(w, p);
    System.out.println(Arrays.toString(result));
  }
}