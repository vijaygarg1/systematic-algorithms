// LLP-StableMarriage: Gale-Shapley as a lattice-linear program, written
// exactly as bxx-stableMatching.tex's Algorithm LLP-StableMarriage:
//
// Always    (i): z = mpref[i][G[i]]
// Forbidden (i): (G[i] = 0) or ((partner[z] != null) and
// (rank[z][partner[z]] < rank[z][i]))
// Advance      : G[i] := G[i] + 1;
// if ((partner[z] = null) or (rank[z][i] < rank[z][partner[z]]))
// then partner[z] := i
//
// setBase(1) and the [0..n] declarations give the book's array[1..n]:
// every array is n+1 long and slot 0 is never used, so the indices are
// 1..n and `mpref[i][G[i]]` is the book's expression with no offset. That
// is also what makes G[i] = 0 safe to read -- it lands on the unused slot
// 0, and the guard's first disjunct makes i forbidden regardless.
//
// G[i] is the book's position in man i's preference list, counted from 1:
// 0 means he has proposed to nobody, k that he is standing on his k-th
// choice. LLPStableMarriageSpec.llp uses the same G.
//
// null is 0, which is not a woman because women are 1..n.
//
// The one thing LL cannot say the book's way: its Always is a definition,
// re-read wherever it appears, so the advance's z is the NEXT choice,
// read after G[i] is incremented. LL's `always` binds once on entry to
// each of forbidden and advance, so the advance re-reads it explicitly.
//
// partner[z] is the best man who has proposed to z so far: redundant
// state, recoverable from G in O(n), kept only so the guard costs O(1)
// rather than the Spec's rescan of every man's proposal history.

import java.util.*;

public class LLPStableMarriage {
  int n;
  int[][] mpref;
  int[][] rank;
  int[] G;
  int[] partner;
  int i;

  private boolean forbidden(int i) {
    int z = mpref[i][G[i]];
    if (!(((G[i] == 0) || ((partner[z] != 0) && (rank[z][partner[z]] < rank[z][i]))))) return false;
    return true;
  }

  private void advance() {
    int z = mpref[i][G[i]];
    G[i] = (G[i] + 1);
    z = mpref[i][G[i]];
    if (((partner[z] == 0) || (rank[z][i] < rank[z][partner[z]]))) {
      partner[z] = i;
    }
  }

  public int[] LLPStableMarriage(int[][] mpref, int[][] rank) {
    this.mpref = mpref;
    this.rank = rank;
    this.n = mpref.length - 1;
    this.G = new int[(n) + 1];
    this.partner = new int[(n) + 1];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 1; i <= n; i++) {
          if (forbidden(i)) {
            this.i = i; advance();
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
    LLPStableMarriage prog = new LLPStableMarriage();
    int[] result = prog.LLPStableMarriage(mpref, rank);
    System.out.println(Arrays.toString(result));
  }
}