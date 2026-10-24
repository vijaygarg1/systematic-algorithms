// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns null on negative cycle.
//
// Calls the sibling Dijkstra program (`Dijkstra(adj, wPrime, u)`, compiling
// to `new Dijkstra().shortestPath(adj, wPrime, u)`) instead of keeping its own
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
//
// The graph is a weighted adjacency list: adj[i] holds the out-neighbours
// of i and the parallel aw[i] their edge weights, so the weight of
// (i, adj[i][t]) is aw[i][t]. The reweighted graph is stored the same way,
// in awPrime, which shares adj since reweighting changes no edge -- so the
// only Theta(n^2) object left is the all-pairs output D itself, which is
// Theta(n^2) by definition.
//
// Both relaxation loops walk adj[i] rather than all n candidate heads.
// That is what gives the book's O(nm log n): step 1 is n-1 passes over m
// edges, and step 4 is n Dijkstra runs of O(m log n) each. Scanning all
// pairs instead makes step 1 Theta(n^3) on its own, which already exceeds
// the bound on any sparse graph.
//
// No edge weight is ever INF here: every pair in adj is a real edge, so
// the "w[i][j] < INF" guards the matrix version needed are gone.

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