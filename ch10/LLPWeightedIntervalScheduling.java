// LLP weighted interval scheduling. The book states B as two families of
// inequalities, G[j] >= G[j-1] and G[j] >= w[j] + G[p[j]], which is
// exactly what one `ensure` over their maximum says; the forbidden
// predicate and advance are then read off B rather than restated.
// Index 0 is pinned at 0, so the guard ranges over 1..n.

import java.util.*;

public class LLPWeightedIntervalScheduling {
  int n;
  int[] w;
  int[] p;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if ((G[j] >= Math.max(G[(j - 1)], (w[j] + G[p[j]])))) return false;
    return true;
  }

  private void advance() {
    G[j] = Math.max(G[(j - 1)], (w[j] + G[p[j]]));
  }

  private double priority(int j) {
    return j;
  }

  public int[] LLPWeightedIntervalScheduling(int[] w, int[] p) {
    this.w = w;
    this.p = p;
    this.n = w.length;
    this.G = new int[n];
    java.util.List<int[]> _ord = new java.util.ArrayList<>();
    for (int j = 1; j < n; j++) {
      _ord.add(new int[]{j});
    }
    _ord.sort((_a, _b) -> Double.compare(priority(_a[0]), priority(_b[0])));
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int[] _t : _ord) {
          int j = _t[0];
          if (forbidden(j)) { this.j = j; advance(); changed = true; }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] w = new int[] {0, 0, 1, 2};
    int[] p = new int[] {0, 0, 1, 1};
    LLPWeightedIntervalScheduling prog = new LLPWeightedIntervalScheduling();
    int[] result = prog.LLPWeightedIntervalScheduling(w, p);
    System.out.println(Arrays.toString(result));
  }
}