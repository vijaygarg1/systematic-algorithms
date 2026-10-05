// Constrained-Stable-Matching: stable matching with precedence constraints.
// always: z = mpref[j][G[j]]

import java.util.*;

public class ConstrainedStableMatching {
  int n;
  int[][] mpref;
  int[][] rank;
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
    return ((G[j] == 0) || t1);
  }

  private void advance() {
    G[j] = (G[j] + 1);
  }

  public void ConstrainedStableMatching(int[][] mpref, int[][] rank) {
    this.mpref = mpref;
    this.rank = rank;
    this.G = new int[n];
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
  }

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    ConstrainedStableMatching prog = new ConstrainedStableMatching();
    prog.ConstrainedStableMatching(mpref, rank);
    System.out.println(Arrays.deepToString(mpref));
    System.out.println(Arrays.deepToString(rank));
  }
}