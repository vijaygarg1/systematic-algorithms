// LLP-StableMarriage-Spec: the specification form of Gale-Shapley, exactly
// as bxx-stableMatching.tex's Algorithm LLP-StableMarriage-Spec states it.
//
// Always    (j): z = mpref[j][G[j]]
// Forbidden (j): exists i in [1..n] : exists k in [1..G[i]] :
// (z = mpref[i][k]) and (rank[z][i] < rank[z][j])
// Advance      : G[j] := G[j] + 1
//
// setBase(1) and the [0..n] declarations give the book's array[1..n]:
// every array is n+1 long and slot 0 is never used, so the indices are
// 1..n and mpref[j][G[j]] is the book's expression with no offset.
//
// G[j] is the book's position in man j's preference list, counted from 1,
// and I is its initial value -- the book's standard initialization is
// [1, 1, ..., 1], every man on his first choice. LLPStableMarriage.llp,
// which the book derives from this program, uses the same G, so the two
// return the same vector on the same instance.
//
// The exists-exists scan is what makes this a specification rather than
// an algorithm: an arbitrary initial G gives no way to tell from G alone
// which woman currently holds the best proposal, so the guard re-examines
// every man's whole proposal history. LLPStableMarriage.llp caches that
// in partner[z] and tests it in O(1).

import java.util.*;

public class LLPStableMarriageSpec {
  int n;
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

  private void advance() {
    int z = mpref[j][G[j]];
    G[j] = (G[j] + 1);
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
            this.j = j; advance();
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