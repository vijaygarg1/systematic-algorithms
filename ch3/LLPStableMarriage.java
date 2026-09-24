// LLP form of Gale-Shapley on the proposal-vector lattice.

import java.util.*;

public class LLPStableMarriage {
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
    return t1;
  }

  private void advance() {
    G[j] = (G[j] + 1);
  }

  public int[] LLPStableMarriage(int[][] mpref, int[][] rank, int[] I) {
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
    int[][] mpref = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[][] rank = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] I = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPStableMarriage prog = new LLPStableMarriage();
    int[] result = prog.LLPStableMarriage(mpref, rank, I);
    System.out.println(Arrays.toString(result));
  }
}