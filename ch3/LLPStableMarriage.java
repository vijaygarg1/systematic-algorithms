// LLP-StableMarriage: Gale-Shapley as a lattice-linear program, written
// exactly as bxx-stableMatching.tex's Algorithm LLP-StableMarriage:

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