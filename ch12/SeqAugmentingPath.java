// Classical augmenting-path bipartite matching, matching
// bxx-matchingReduced.tex Algorithm Seq-AugmentingPath (algo:seq-bipartite):
// for each L-vertex j in turn, look for an alternating path to some free
// R-vertex k (S[k] unmatched). The book's abstract "there exists an
// alternating path..." step, plus its "assign S[] along the path"
// follow-up, are both realized here by the alternatingPath(...) DFS --
// the standard way of implementing that check.
//
// The graph is the book's (L, R, E): adj[j] lists the R-neighbours of the
// L-vertex j, and nr is |R|, which cannot be recovered from the lists when
// some R-vertex has no incident edge. A dense |L| x |R| matrix would make
// every DFS step scan all of R however few edges exist -- and reading such
// a matrix is Theta(|L| * |R|) before the algorithm starts, so no bound in
// |E| is reachable from it at all.

import java.util.*;

public class SeqAugmentingPath {
  public int[] SeqAugmentingPath(int[][] adj, int nr) {
    int n = adj.length;
    int m = nr;
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
    int t = 0;
    while ((t < adj[j].length)) {
      int k = adj[j][t];
      if ((!seen[k])) {
        seen[k] = true;
        if (((S[k] == (0 - 1)) || alternatingPath(S[k], adj, S, seen))) {
          S[k] = j;
          return true;
        }
      }
      t = (t + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    SeqAugmentingPath prog = new SeqAugmentingPath();
    int[] result = prog.SeqAugmentingPath(adj, adj.length);
    System.out.println(Arrays.toString(result));
  }
}