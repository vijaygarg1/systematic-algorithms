// LLP-StableMarriage-Spec: a specification of Gale-Shapley, not yet an
// efficient algorithm (bxx-stableMatching.tex, Algorithm
// LLP-StableMarriage-Spec). Its forbidden condition rescans every man's
// whole proposal history to find out who currently holds a given
// woman -- O(n) per guard, O(n^4) total -- because an arbitrary initial
// G gives no cheaper way to tell. It exists only as a correctness
// stepping stone toward LLPStableMarriage.llp, which caches that
// information instead; do not measure or recommend this version on its
// own (see v3performance.txt).

import java.util.*;

public class LLPStableMarriageSpec {
  int n;
  int[][] mpref;
  int[][] rank;
  int[] I;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i = 0; i < n; i++) {
      boolean t2 = false;
      for (int k = 0; k <= G[i]; k++) {
        if (((mpref[j][G[j]] == mpref[i][k]) && (rank[mpref[j][G[j]]][i] < rank[mpref[j][G[j]]][j]))) { t2 = true; break; }
      }
      if (t2) { t1 = true; break; }
    }
    if (!(t1)) return false;
    return true;
  }

  private void advance() {
    G[j] = (G[j] + 1);
  }

  public int[] LLPStableMarriageSpec(int[][] mpref, int[][] rank, int[] I) {
    this.mpref = mpref;
    this.rank = rank;
    this.I = I;
    this.n = I.length;
    this.G = I.clone();
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

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    int[] I = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
    LLPStableMarriageSpec prog = new LLPStableMarriageSpec();
    int[] result = prog.LLPStableMarriageSpec(mpref, rank, I);
    System.out.println(Arrays.toString(result));
  }
}