// LLP-StableMarriage: Gale-Shapley as a lattice-linear program, exactly
// as bxx-stableMatching.tex's Algorithm LLP-StableMarriage states it.
//
// Always    (i): z = mpref[i][G[i]]
// Forbidden (i): (G[i] = 0) or ((partner[z] != null) and
// (rank[z][partner[z]] < rank[z][i]))
// Advance      : G[i] := G[i] + 1;
// if ((partner[z] = null) or (rank[z][i] < rank[z][partner[z]]))
// then partner[z] := i
//
// G[i] is the book's POSITION in man i's preference list, counted from 1:
// G[i] = 0 means he has proposed to nobody, G[i] = k that he is standing
// on his k-th choice. LLPStableMarriageSpec.llp, which the book derives
// this program from, uses the same G, so the two return the same vector.
//
// Two places where the book's 1-indexed arrays do not carry over
// verbatim, and nothing else differs:
//
// The book's mpref is array[1..n][1..n], so its z = mpref[i][G[i]] is
// the woman i is standing on, and G[i] = 0 harmlessly reads the unused
// slot 0. Here mpref is 0-indexed, so the same woman is at G[i]-1 and
// G[i] = 0 would index -1 -- hence max(G[i], 1) - 1, which is slot 0
// when he has not yet proposed. The guard's first disjunct makes him
// forbidden in that case regardless of what z holds.
//
// The book's Always is a definition, re-read wherever it appears, so
// the advance's z is the NEXT choice -- read after G[i] is
// incremented. LL's `always` binds once on entry to each of forbidden
// and advance, so the advance re-reads it explicitly.
//
// partner[z] (-1 for null) is the best man who has proposed to z so far:
// redundant state, recoverable from G in O(n), kept only so the guard is
// O(1) rather than the Spec's rescan of every man's proposal history.

import java.util.*;

public class LLPStableMarriage {
  int n;
  int[][] mpref;
  int[][] rank;
  int[] G;
  int[] partner;
  int i;

  private boolean _forbidden0(int i) {
    int z = mpref[i][(Math.max(G[i], 1) - 1)];
    if (!(((G[i] == 0) || ((partner[z] != (-1)) && (rank[z][partner[z]] < rank[z][i]))))) return false;
    return true;
  }

  private void _advance0() {
    int z = mpref[i][(Math.max(G[i], 1) - 1)];
    G[i] = (G[i] + 1);
    z = mpref[i][(G[i] - 1)];
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
    for (int w = 0; w < n; w++) {
      partner[w] = (-1);
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

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    LLPStableMarriage prog = new LLPStableMarriage();
    int[] result = prog.LLPStableMarriage(mpref, rank);
    System.out.println(Arrays.toString(result));
  }
}