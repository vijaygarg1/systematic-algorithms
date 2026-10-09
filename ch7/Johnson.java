// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns null on negative cycle.
//
// Calls the sibling Dijkstra program (`Dijkstra(wPrime, u)`, compiling to
// `new Dijkstra().shortestPath(wPrime, u)`) instead of keeping its own
// copy of the single-source relaxation loop -- confirmed correct against
// the standard CLRS Johnson's-algorithm worked example. This makes
// Johnson.java a multi-file program: it requires Dijkstra.java alongside
// it to compile (validate-site.py's Java check and the website's
// download links both know about this dependency; see JAVA_MULTI_FILE
// in validate-site.py and the `javaRequires` demo-spec field in llp.js).

import java.util.*;

public class Johnson {
  public int[][] allPairs(int[][] w) {
    int n = w.length;
    int INF = Integer.MAX_VALUE;
    int[] dist = new int[n];
    for (int i = 0; i < n; i++) {
      dist[i] = 0;
    }
    int k = 0;
    while ((k < (n - 1))) {
      boolean changed = false;
      int i = 0;
      while ((i < n)) {
        int j = 0;
        while ((j < n)) {
          if (((w[i][j] < INF) && ((dist[i] + w[i][j]) < dist[j]))) {
            dist[j] = (dist[i] + w[i][j]);
            changed = true;
          }
          j = (j + 1);
        }
        i = (i + 1);
      }
      if ((!changed)) {
        k = n;
      } else {
        k = (k + 1);
      }
    }
    int i = 0;
    while ((i < n)) {
      int j = 0;
      while ((j < n)) {
        if (((w[i][j] < INF) && ((dist[i] + w[i][j]) < dist[j]))) {
          return null;
        }
        j = (j + 1);
      }
      i = (i + 1);
    }
    int[] price = new int[n];
    for (int v = 0; v < n; v++) {
      price[v] = (0 - dist[v]);
    }
    int[][] wPrime = new int[n][n];
    i = 0;
    while ((i < n)) {
      int j = 0;
      while ((j < n)) {
        if ((w[i][j] < INF)) {
          wPrime[i][j] = ((w[i][j] + price[j]) - price[i]);
        } else {
          wPrime[i][j] = INF;
        }
        j = (j + 1);
      }
      i = (i + 1);
    }
    int[][] D = new int[n][n];
    int u = 0;
    while ((u < n)) {
      int[] distPrime = new Dijkstra().shortestPath(wPrime, u);
      int v = 0;
      while ((v < n)) {
        if ((distPrime[v] < INF)) {
          D[u][v] = ((distPrime[v] + price[u]) - price[v]);
        } else {
          D[u][v] = INF;
        }
        v = (v + 1);
      }
      u = (u + 1);
    }
    return D;
  }

  public static void main(String[] args) {
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    Johnson prog = new Johnson();
    int[][] result = prog.allPairs(w);
    System.out.println(Arrays.deepToString(result));
  }
}