// Constrained-Stable-Matching: stable matching with precedence constraints.
// always: z = mpref[j][G[j]]

import java.util.*;

public class ConstrainedStableMatching {
  int n;
  int[][] mpref;
  int[][] rank;
  int[] G;

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

  private void advance(int j) {
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
            advance(j);
            changed = true;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[][] rank = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    ConstrainedStableMatching prog = new ConstrainedStableMatching();
    prog.ConstrainedStableMatching(mpref, rank);
    System.out.println(Arrays.deepToString(mpref));
    System.out.println(Arrays.deepToString(rank));
  }
}