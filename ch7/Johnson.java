// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns null on negative cycle.
//
// Keeps its own copy of the single-source relaxation loop (`dijkstra`
// below) rather than calling the sibling Dijkstra program, even though
// llc.py/llcjs.py can now express that call (added for exactly this,
// chat-companion review item 2: `int[] distPrime = Dijkstra(wPrime, u);`
// compiles correctly as `new Dijkstra().shortestPath(wPrime, u)`).
// Tried it directly -- confirmed correct output against the standard
// CLRS Johnson's-algorithm worked example -- but it breaks a site-wide
// guarantee this companion otherwise holds for every single program:
// every published Java/Python/C++/Rust file runs completely standalone
// when downloaded alone (the "Plain text" links on each demo page link
// to exactly one file, never a bundle). A Johnson.java that references
// `new Dijkstra()` needs Dijkstra.java alongside it to even compile --
// confirmed validate-site.py's per-file isolated compile check catches
// this immediately, the same class of problem the Kruskal.py fix
// (progs-mst/py/Kruskal.py) addressed by inlining rather than
// importing. Kept duplicated for the same reason until/unless this site
// starts publishing multi-file downloads.

import java.util.*;

public class Johnson {
  public int[][] allPairs(int[][] w) {
    int n = w.length;
    int INF = 2147483647;
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
      int[] distPrime = dijkstra(wPrime, u, n);
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

  public int[] dijkstra(int[][] w, int s, int n) {
    int INF = 2147483647;
    int[] dist = new int[n];
    boolean[] fixed = new boolean[n];
    for (int i = 0; i < n; i++) {
      dist[i] = INF;
    }
    dist[s] = 0;
    int count = 0;
    while ((count < n)) {
      int j = (0 - 1);
      int best = INF;
      int k = 0;
      while ((k < n)) {
        if (((!fixed[k]) && (dist[k] < best))) {
          j = k;
          best = dist[k];
        }
        k = (k + 1);
      }
      if ((j == (0 - 1))) {
        return dist;
      }
      fixed[j] = true;
      count = (count + 1);
      k = 0;
      while ((k < n)) {
        if ((((!fixed[k]) && (w[j][k] < INF)) && ((dist[j] + w[j][k]) < dist[k]))) {
          dist[k] = (dist[j] + w[j][k]);
        }
        k = (k + 1);
      }
    }
    return dist;
  }

  public static void main(String[] args) {
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    Johnson prog = new Johnson();
    int[][] result = prog.allPairs(w);
    System.out.println(Arrays.deepToString(result));
  }
}