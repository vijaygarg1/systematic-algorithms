// LLP-StableMarriage-Spec: the specification form of Gale-Shapley, as
// bxx-stableMatching.tex's Algorithm LLP-StableMarriage-Spec states it.
//
// Always    (j): z = mpref[j][G[j]]
// Forbidden (j): exists i in [1..n] : exists k in [1..G[i]] :
// (z = mpref[i][k]) and (rank[z][i] < rank[z][j])
// Advance      : G[j] := G[j] + 1
//
// G[j] is the book's POSITION in man j's preference list, counted from 1:
// G[j] = k means j is standing on his k-th choice. The book's arrays are
// 1-indexed, so its mpref[j][G[j]] is written mpref[j][G[j]-1] here, and
// its k in [1..G[i]] stays [1..G[i]] with the lookup mpref[i][k-1].
//
// Keeping the book's 1-based POSITION rather than storing a 0-based index
// matters: LLPStableMarriage.llp, which the book derives from this
// program, uses the same G, so the two return the same vector on the same
// instance. Storing k-1 here instead made the two disagree by exactly one
// everywhere, for the same matching.
//
// I is the initial position vector; the book's standard initialization is
// [1, 1, ..., 1], every man on his first choice. G[j] is never 0 -- that
// is LLPStableMarriage's "has proposed to nobody", a state this
// specification does not use.
//
// The exists-exists scan is why this is a specification and not yet an
// algorithm: an arbitrary initial G gives no way to tell from G alone
// which woman currently holds the best proposal, so the guard re-examines
// every man's whole proposal history. LLPStableMarriage.llp caches that
// in partner[z] instead.

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
      for (int k = 1; k <= G[i]; k++) {
        if (((mpref[i][(k - 1)] == mpref[j][(G[j] - 1)]) && (rank[mpref[j][(G[j] - 1)]][i] < rank[mpref[j][(G[j] - 1)]][j]))) { t2 = true; break; }
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
    this.n = mpref.length;
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
    int[] I = new int[] {1, 1, 1, 1, 1, 1, 1, 1};
    LLPStableMarriageSpec prog = new LLPStableMarriageSpec();
    int[] result = prog.LLPStableMarriageSpec(mpref, rank, I);
    System.out.println(Arrays.toString(result));
  }
}