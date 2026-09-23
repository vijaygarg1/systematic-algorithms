// Ford-Fulkerson max-flow via DFS-found augmenting paths.

import java.util.*;

public class FordFulkerson {
  public int[][] maxflow(int[][] c, int s, int t) {
    int n = c.length;
    int[][] f = new int[n][n];
    int[] parent = new int[n];
    boolean done = false;
    while ((!done)) {
      int found = augmentingPath(c, f, s, t, parent);
      if ((found == 0)) {
        done = true;
      } else {
        int bottleneck = 2147483647;
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

  public int augmentingPath(int[][] c, int[][] f, int s, int t, int[] parent) {
    int n = c.length;
    boolean[] seen = new boolean[n];
    int[] stack = new int[n];
    int top = 0;
    stack[top] = s;
    top = (top + 1);
    seen[s] = true;
    parent[s] = s;
    while ((top > 0)) {
      top = (top - 1);
      int u = stack[top];
      if ((u == t)) {
        return 1;
      }
      int v = 0;
      while ((v < n)) {
        if (((!seen[v]) && (residual(c, f, u, v) > 0))) {
          seen[v] = true;
          parent[v] = u;
          stack[top] = v;
          top = (top + 1);
        }
        v = (v + 1);
      }
    }
    return 0;
  }

  public int residual(int[][] c, int[][] f, int u, int v) {
    return (c[u][v] - f[u][v]);
  }

  public static void main(String[] args) {
    int[][] c = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    FordFulkerson prog = new FordFulkerson();
    int[][] result = prog.maxflow(c, 0, 1);
    System.out.println(Arrays.deepToString(result));
  }
}