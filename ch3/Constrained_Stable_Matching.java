// Constrained-Stable-Matching: stable matching with precedence constraints.

import java.util.*;

public class Constrained_Stable_Matching {
  int n;
  int[][] mpref;
  int[][] rank;
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
    return ((G[j] == 0) || t1);
  }

  private void advance() {
    int z = mpref[j][G[j]];
    G[j] = (G[j] + 1);
  }

  public void Constrained_Stable_Matching(int[][] mpref, int[][] rank, int n) {
    this.mpref = mpref;
    this.rank = rank;
    this.n = n;
    this.G = new int[n + 1];
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
  }

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    int n = 0;
    Constrained_Stable_Matching prog = new Constrained_Stable_Matching();
    prog.Constrained_Stable_Matching(mpref, rank, n);
    System.out.println(Arrays.deepToString(mpref));
    System.out.println(Arrays.deepToString(rank));
  }
}