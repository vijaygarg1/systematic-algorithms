// Classical augmenting-path bipartite matching, matching
// bxx-matchingReduced.tex Algorithm BipartiteMatching (algo:seq-bipartite):
// for each L-vertex j in turn, look for an alternating path to some free
// R-vertex k (S[k] unmatched). The book's abstract "there exists an
// alternating path..." step, plus its "assign S[] along the path"
// follow-up, are both realized here by the alternatingPath(...) DFS --
// the standard way of implementing that check.

import java.util.*;

public class BipartiteMatching {
  public int[] BipartiteMatching(int[][] adj) {
    int n = adj.length;
    int m = adj[0].length;
    int[] G = new int[n];
    int[] S = new int[m];
    for (int j = 0; j < n; j++) {
      G[j] = 0;
    }
    for (int k = 0; k < m; k++) {
      S[k] = (0 - 1);
    }
    for (int j = 0; j < n; j++) {
      {
        boolean[] seen = new boolean[m];
        if (alternatingPath(j, adj, S, seen)) {
          G[j] = 1;
        }
      }
    }
    return G;
  }

  public boolean alternatingPath(int j, int[][] adj, int[] S, boolean[] seen) {
    int m = adj[0].length;
    int k = 0;
    while ((k < m)) {
      if (((adj[j][k] == 1) && (!seen[k]))) {
        seen[k] = true;
        if (((S[k] == (0 - 1)) || alternatingPath(S[k], adj, S, seen))) {
          S[k] = j;
          return true;
        }
      }
      k = (k + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    BipartiteMatching prog = new BipartiteMatching();
    int[] result = prog.BipartiteMatching(adj);
    System.out.println(Arrays.toString(result));
  }
}