// LLP-StableMarriage-Spec: specification form of Gale-Shapley (bxx-stableMatching.tex).
// G[j] = man j's current proposal index, 1 = top choice; I is its initial value, 1<=I[i]<=n.
// Guard restored 2026-10-26: without it a bad I runs G[j] to n+1 and reads past mpref's row.

import java.util.*;

public class LLPStableMarriageSpec {
  int n;
  private static final int[] _NO_EARLY_EXIT = new int[0];
  int[][] mpref;
  int[][] rank;
  int[] I;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int z = mpref[j][G[j]];
    boolean t1 = false;
    for (int i = 1; i <= n; i++) {
      boolean t2 = false;
      for (int k = 1; k <= G[i]; k++) {
        if (((mpref[i][k] == z) && (rank[z][i] < rank[z][j]))) { t2 = true; break; }
      }
      if (t2) { t1 = true; break; }
    }
    if (!(t1)) return false;
    return true;
  }

  private int[] advance() {
    int z = mpref[j][G[j]];
    if ((G[j] == n)) {
      return null;
    }
    G[j] = (G[j] + 1);
    return _NO_EARLY_EXIT;
  }

  public int[] LLPStableMarriageSpec(int[][] mpref, int[][] rank, int[] I) {
    this.mpref = mpref;
    this.rank = rank;
    this.I = I;
    this.n = I.length - 1;
    this.G = I.clone();
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 1; j <= n; j++) {
          if (forbidden(j)) {
            this.j = j; int[] _r = advance(); if (_r != _NO_EARLY_EXIT) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 2, 3, 4, 5, 6, 7}, {0, 2, 3, 4, 5, 6, 7, 1}, {0, 3, 4, 5, 6, 7, 1, 2}, {0, 4, 5, 6, 7, 1, 2, 3}, {0, 5, 6, 7, 1, 2, 3, 4}, {0, 6, 7, 1, 2, 3, 4, 5}, {0, 7, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 7, 6, 5, 4, 3, 2}, {0, 2, 1, 7, 6, 5, 4, 3}, {0, 3, 2, 1, 7, 6, 5, 4}, {0, 4, 3, 2, 1, 7, 6, 5}, {0, 5, 4, 3, 2, 1, 7, 6}, {0, 6, 5, 4, 3, 2, 1, 7}, {0, 7, 6, 5, 4, 3, 2, 1}};
    int[] I = new int[] {0, 1, 1, 1, 1, 1, 1, 1};
    LLPStableMarriageSpec prog = new LLPStableMarriageSpec();
    int[] result = prog.LLPStableMarriageSpec(mpref, rank, I);
    System.out.println(Arrays.toString(result));
  }
}