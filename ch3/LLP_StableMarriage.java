// LLP form of Gale-Shapley on the proposal-vector lattice.

import java.util.*;

public class LLP_StableMarriage {
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
        if (((z == mpref[i][k]) && (rank[z][i] < rank[z][j]))) { t2 = true; break; }
      }
      if (t2) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    int z = mpref[j][G[j]];
    G[j] = (G[j] + 1);
  }

  public int[] LLP_StableMarriage(int[][] mpref, int[][] rank, int[] I) {
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
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    int[] I = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
    LLP_StableMarriage prog = new LLP_StableMarriage();
    int[] result = prog.LLP_StableMarriage(mpref, rank, I);
    System.out.println(Arrays.toString(result));
  }
}