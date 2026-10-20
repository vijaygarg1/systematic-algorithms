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
//
// The book's box says "Run BellmanFord" and "Run Dijkstra" symmetrically,
// but only Dijkstra is called here; step 1 keeps its own relaxation loop.
// That is a capability gap, not an oversight. Sibling BellmanFord.llp
// takes an edge list -- shortestPath(n, U, V, W, s) -- where Johnson has
// an adjacency matrix, and it runs its n-1 passes and returns dist with
// no extra pass and no way to report a negative cycle. Johnson needs
// exactly that report, so calling it would drop the check the book names
// explicitly.

import java.util.*;

public class Johnson {
  public int[][] allPairs(int[][] w) {
    int n = w.length;
    int INF = Integer.MAX_VALUE;
    int[] dist = new int[n];
    int k = 0;
    while ((k < (n - 1))) {
      boolean changed = false;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (((w[i][j] < INF) && ((dist[i] + w[i][j]) < dist[j]))) {
            dist[j] = (dist[i] + w[i][j]);
            changed = true;
          }
        }
      }
      if (changed) {
        k = (k + 1);
      } else {
        k = n;
      }
    }
    if (_q1(n, INF, k, dist, w)) {
      return null;
    }
    int[] price = new int[n];
    for (int v = 0; v < n; v++) {
      price[v] = (0 - dist[v]);
    }
    int[][] wPrime = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        wPrime[i][j] = reweight(w[i][j], price[j], price[i]);
      }
    }
    int[][] D = new int[n][n];
    int u = 0;
    while ((u < n)) {
      int[] distPrime = new Dijkstra().shortestPath(wPrime, u);
      for (int v = 0; v < n; v++) {
        D[u][v] = reweight(distPrime[v], price[u], price[v]);
      }
      u = (u + 1);
    }
    return D;
  }

  public int reweight(int len, int plus, int minus) {
    if ((len >= Integer.MAX_VALUE)) {
      return Integer.MAX_VALUE;
    }
    return ((len + plus) - minus);
  }

  private boolean _q2(int n, int INF, int k, int i, int[] dist, int[][] w) {
    for (int j = 0; j < n; j++) {
      if (((w[i][j] < INF) && ((dist[i] + w[i][j]) < dist[j]))) return true;
    }
    return false;
  }

  private boolean _q1(int n, int INF, int k, int[] dist, int[][] w) {
    for (int i = 0; i < n; i++) {
      if (_q2(n, INF, k, i, dist, w)) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    Johnson prog = new Johnson();
    int[][] result = prog.allPairs(w);
    System.out.println(Arrays.deepToString(result));
  }
}