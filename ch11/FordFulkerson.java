// Classical Ford-Fulkerson maximum flow: repeatedly find an augmenting
// path in the residual graph by depth-first search and push the largest
// amount that fits along it, until no augmenting path remains.

import java.util.*;

public class FordFulkerson {
  public int[][] maxflow(int[][] c, int s, int t) {
    int n = c.length;
    int[][] f = new int[n][n];
    int[] parent = new int[n];
    while ((findAugmentingPath(c, f, s, t, parent) == 1)) {
      augment(f, s, t, parent, bottleneck(c, f, s, t, parent));
    }
    return f;
  }

  public int bottleneck(int[][] c, int[][] f, int s, int v, int[] parent) {
    if ((v == s)) {
      return Integer.MAX_VALUE;
    }
    return Math.min(residual(c, f, parent[v], v), bottleneck(c, f, s, parent[v], parent));
  }

  public void augment(int[][] f, int s, int v, int[] parent, int b) {
    if ((v == s)) {
      return;
    }
    int u = parent[v];
    f[u][v] = (f[u][v] + b);
    f[v][u] = (f[v][u] - b);
    augment(f, s, u, parent, b);
  }

  public int findAugmentingPath(int[][] c, int[][] f, int s, int t, int[] parent) {
    boolean[] seen = new boolean[c.length];
    seen[s] = true;
    parent[s] = s;
    return dfs(c, f, s, t, parent, seen);
  }

  public int dfs(int[][] c, int[][] f, int u, int t, int[] parent, boolean[] seen) {
    if ((u == t)) {
      return 1;
    }
    for (int v = 0; v < c.length; v++) {
      {
        if (((!seen[v]) && (residual(c, f, u, v) > 0))) {
          seen[v] = true;
          parent[v] = u;
          if ((dfs(c, f, v, t, parent, seen) == 1)) {
            return 1;
          }
        }
      }
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