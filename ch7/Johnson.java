// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns null on negative cycle.

import java.util.*;

public class Johnson {
  static final int INF = 2147483647;

  public int[][] allPairs(int[][] w) {
    int n = w.length;
    // Step 1: BellmanFord from auxiliary source s* with weight-0 edges to
    // every v. The auxiliary edge is encoded by initializing dist[i] = 0;
    // n-1 relaxation passes then suffice over the n original vertices.
    int[] dist = new int[n];
    for (int i = 0; i < n; i++) dist[i] = 0;
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
    // One more pass: any further relaxation indicates a negative cycle.
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
    // Step 2: price[v] = -dist[v]. All prices are non-negative.
    int[] price = new int[n];
    i = 0;
    while ((i < n)) {
      price[i] = (0 - dist[i]);
      i = (i + 1);
    }
    // Step 3: reweight every edge: w'[i,j] = w[i,j] + price[j] - price[i].
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
    // Step 4: Dijkstra from every source on the reweighted graph; undo the
    // reweighting on the way out: D[u,v] = dist'[u,v] + price[u] - price[v].
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

  private int[] dijkstra(int[][] w, int s, int n) {
    int[] dist = new int[n];
    boolean[] fixed = new boolean[n];
    int i = 0;
    while ((i < n)) {
      dist[i] = INF;
      i = (i + 1);
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
        if (((!fixed[k]) && (w[j][k] < INF))) {
          if (((dist[j] + w[j][k]) < dist[k])) {
            dist[k] = (dist[j] + w[j][k]);
          }
        }
        k = (k + 1);
      }
    }
    return dist;
  }

  public static void main(String[] args) {
    // 3-vertex directed graph: A->B = 2, B->C = -1, A->C = 4.
    int[][] w = new int[][] {
      {0, 2, 4},
      {INF, 0, (0 - 1)},
      {INF, INF, 0}
    };
    Johnson prog = new Johnson();
    int[][] D = prog.allPairs(w);
    if ((D == null)) {
      System.out.println("negative-weight cycle");
    } else {
      System.out.println(Arrays.deepToString(D));
    }
  }
}
