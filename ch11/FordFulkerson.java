// Ford-Fulkerson max-flow: repeatedly find an augmenting path in the
// residual graph via DFS, push its bottleneck capacity, until none
// remains. Shares its outer loop verbatim with EdmondsKarp.llp, which
// differs only in finding augmenting paths via BFS instead of DFS.

import java.util.*;

public class FordFulkerson {
  public int[][] maxflow(int[][] c, int s, int t) {
    int n = c.length;
    int[][] f = new int[n][n];
    int[] parent = new int[n];
    boolean done = false;
    while ((!done)) {
      int found = findAugmentingPath(c, f, s, t, parent);
      if ((found == 0)) {
        done = true;
      } else {
        int bottleneck = Integer.MAX_VALUE;
        int v = t;
        while ((v != s)) {
          int u = parent[v];
          int r = residual(c, f, u, v);
          if ((r < bottleneck)) {
            bottleneck = r;
          }
          v = u;
        }
        v = t;
        while ((v != s)) {
          int u = parent[v];
          f[u][v] = (f[u][v] + bottleneck);
          f[v][u] = (f[v][u] - bottleneck);
          v = u;
        }
      }
    }
    return f;
  }

  public int findAugmentingPath(int[][] c, int[][] f, int s, int t, int[] parent) {
    int n = c.length;
    boolean[] seen = new boolean[n];
    seen[s] = true;
    parent[s] = s;
    return dfs(c, f, s, t, parent, seen);
  }

  public int dfs(int[][] c, int[][] f, int u, int t, int[] parent, boolean[] seen) {
    if ((u == t)) {
      return 1;
    }
    int n = c.length;
    int v = 0;
    while ((v < n)) {
      if (((!seen[v]) && (residual(c, f, u, v) > 0))) {
        seen[v] = true;
        parent[v] = u;
        if ((dfs(c, f, v, t, parent, seen) == 1)) {
          return 1;
        }
      }
      v = (v + 1);
    }
    return 0;
  }

  public int residual(int[][] c, int[][] f, int u, int v) {
    return (c[u][v] - f[u][v]);
  }

  public static void main(String[] args) {
    int[][] c = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    FordFulkerson prog = new FordFulkerson();
    int[][] result = prog.maxflow(c, 0, 1);
    System.out.println(Arrays.deepToString(result));
  }
}