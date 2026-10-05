// Classical augmenting-path bipartite matching.

import java.util.*;

public class BipartiteMatching {
  public int[] BipartiteMatching(int[][] adj) {
    int n = adj.length;
    int m = adj[0].length;
    int[] G = new int[n];
    int[] partner = new int[m];
    int j = 0;
    while ((j < m)) {
      partner[j] = (0 - 1);
      j = (j + 1);
    }
    int u = 0;
    while ((u < n)) {
      boolean[] seen = new boolean[m];
      if (tryMatch(u, adj, partner, seen)) {
        G[u] = 1;
      }
      u = (u + 1);
    }
    return G;
  }

  public boolean tryMatch(int u, int[][] adj, int[] partner, boolean[] seen) {
    int m = adj[0].length;
    int v = 0;
    while ((v < m)) {
      if (((adj[u][v] == 1) && (!seen[v]))) {
        seen[v] = true;
        if (((partner[v] == (0 - 1)) || tryMatch(partner[v], adj, partner, seen))) {
          partner[v] = u;
          return true;
        }
      }
      v = (v + 1);
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