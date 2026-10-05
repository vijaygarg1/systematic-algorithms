// Edmonds-Karp: Ford-Fulkerson with BFS to pick shortest augmenting paths.

import java.util.*;

public class EdmondsKarp {
  public int[][] maxflow(int[][] c, int s, int t) {
    int n = c.length;
    int[][] f = new int[n][n];
    int[] parent = new int[n];
    boolean done = false;
    while ((!done)) {
      int found = bfsResidual(c, f, s, t, parent);
      if ((found == 0)) {
        done = true;
      } else {
        int bottleneck = 2147483647;
        int v = t;
        while ((v != s)) {
          int u = parent[v];
          int r = (c[u][v] - f[u][v]);
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

  public int bfsResidual(int[][] c, int[][] f, int s, int t, int[] parent) {
    int n = c.length;
    boolean[] seen = new boolean[n];
    int[] q = new int[n];
    int head = 0;
    int tail = 0;
    q[tail] = s;
    tail = (tail + 1);
    seen[s] = true;
    parent[s] = s;
    while ((head < tail)) {
      int u = q[head];
      head = (head + 1);
      if ((u == t)) {
        return 1;
      }
      int v = 0;
      while ((v < n)) {
        if (((!seen[v]) && ((c[u][v] - f[u][v]) > 0))) {
          seen[v] = true;
          parent[v] = u;
          q[tail] = v;
          tail = (tail + 1);
        }
        v = (v + 1);
      }
    }
    if (seen[t]) {
      return 1;
    }
    return 0;
  }

  public static void main(String[] args) {
    int[][] c = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    EdmondsKarp prog = new EdmondsKarp();
    int[][] result = prog.maxflow(c, 0, 1);
    System.out.println(Arrays.deepToString(result));
  }
}