// LLP-StableMarriage: Gale-Shapley as a lattice-linear program, exactly
// as bxx-stableMatching.tex's Algorithm LLP-StableMarriage states it.
//
// Always    (i): z = mpref[i][G[i]]
// Forbidden (i): (G[i] = 0) or ((partner[z] != null) and
// (rank[z][partner[z]] < rank[z][i]))
// Advance      : G[i] := G[i] + 1;
// if ((partner[z] = null) or (rank[z][i] < rank[z][partner[z]])):
// partner[z] := i
//
// G[i] is a PROPOSAL COUNT: how many proposals man i has made, 0..n. The
// book's arrays are 1-indexed, so its z = mpref[i][G[i]] is the proposal
// man i is currently standing on; mpref here is 0-indexed, so the same
// woman is mpref[i][G[i]-1], and G[i] = 0 means he has proposed to
// nobody yet (the guard's first disjunct, which short-circuits before z
// is ever needed).
//
// z is written out at each use rather than declared with LL's `always`.
// The book's Always is a definition, re-read wherever it appears, and
// the advance reads it AFTER incrementing G[i] -- man i proposes to his
// NEXT choice. llc.py's `always` instead binds once on entry to each of
// forbidden() and advance(), so the advance would propose to the woman
// who had just rejected him.
//
// partner[z] (-1 if unmatched) is the best man who has proposed to z so
// far. It is redundant state, recoverable from G in O(n), and exists
// only so the guard is O(1): LLPStableMarriageSpec.llp is the same
// algorithm with the guard rescanning every man's whole proposal
// history instead, and the book derives this program from that one.

import java.util.*;

public class LLPStableMarriage {
  int n;
  int[][] mpref;
  int[][] rank;
  int[] G;
  int[] partner;
  int i;

  private boolean _forbidden0(int i) {
    if (!(((G[i] == 0) || rejected(i)))) return false;
    return true;
  }

  private void _advance0() {
    G[i] = (G[i] + 1);
    int z = mpref[i][(G[i] - 1)];
    if (((partner[z] == (-1)) || (rank[z][i] < rank[z][partner[z]]))) {
      partner[z] = i;
    }
  }

  public int[] LLPStableMarriage(int[][] mpref, int[][] rank) {
    this.mpref = mpref;
    this.rank = rank;
    this.n = mpref.length;
    this.G = new int[n];
    this.partner = new int[n];
    for (int z = 0; z < n; z++) {
      partner[z] = (-1);
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 0; i < n; i++) {
          if (_forbidden0(i)) {
            this.i = i; _advance0();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean rejected(int i) {
    int z = mpref[i][(G[i] - 1)];
    return ((partner[z] != (-1)) && (rank[z][partner[z]] < rank[z][i]));
  }

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    LLPStableMarriage prog = new LLPStableMarriage();
    int[] result = prog.LLPStableMarriage(mpref, rank);
    System.out.println(Arrays.toString(result));
  }
}