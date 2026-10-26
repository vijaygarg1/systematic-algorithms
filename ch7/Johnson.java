// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns null on negative cycle.

import java.util.*;

public class Johnson {
  public int[][] allPairs(int[][] adj, int[][] aw) {
    int n = adj.length;
    int INF = Integer.MAX_VALUE;
    int[] dist = new int[n];
    int k = 0;
    while ((k < (n - 1))) {
      boolean changed = false;
      for (int i = 0; i < n; i++) {
        {
          int t = 0;
          while ((t < adj[i].length)) {
            int j = adj[i][t];
            if (((dist[i] + aw[i][t]) < dist[j])) {
              dist[j] = (dist[i] + aw[i][t]);
              changed = true;
            }
            t = (t + 1);
          }
        }
      }
      if (changed) {
        k = (k + 1);
      } else {
        k = n;
      }
    }
    boolean negCycle = false;
    for (int i = 0; i < n; i++) {
      {
        int t = 0;
        while ((t < adj[i].length)) {
          if (((dist[i] + aw[i][t]) < dist[adj[i][t]])) {
            negCycle = true;
          }
          t = (t + 1);
        }
      }
    }
    if (negCycle) {
      return null;
    }
    int[] price = new int[n];
    for (int v = 0; v < n; v++) {
      price[v] = (0 - dist[v]);
    }
    int[][] awPrime = new int[n][];
    for (int i = 0; i < n; i++) {
      awPrime[i] = new int[adj[i].length];
    }
    for (int i = 0; i < n; i++) {
      {
        int t = 0;
        while ((t < adj[i].length)) {
          awPrime[i][t] = reweight(aw[i][t], price[adj[i][t]], price[i]);
          t = (t + 1);
        }
      }
    }
    int[][] D = new int[n][n];
    for (int u = 0; u < n; u++) {
      {
        int[] distPrime = new Dijkstra().shortestPath(adj, awPrime, u);
        for (int v = 0; v < n; v++) {
          D[u][v] = reweight(distPrime[v], price[u], price[v]);
        }
      }
    }
    return D;
  }

  public int reweight(int len, int plus, int minus) {
    if ((len >= Integer.MAX_VALUE)) {
      return Integer.MAX_VALUE;
    }
    return ((len + plus) - minus);
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] aw = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    Johnson prog = new Johnson();
    int[][] result = prog.allPairs(adj, aw);
    System.out.println(Arrays.deepToString(result));
  }
}