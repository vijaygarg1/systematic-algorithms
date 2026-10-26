// Classical Bellman-Ford: n-1 relaxation passes over every edge.

import java.util.*;

public class BellmanFord {
  public int[] shortestPath(int n, int[] U, int[] V, int[] W, int s) {
    int[] dist = new int[n];
    for (int i = 0; i < n; i++) {
      dist[i] = Integer.MAX_VALUE;
    }
    dist[s] = 0;
    for (int k = 1; k < n; k++) {
      {
        for (int e = 0; e < U.length; e++) {
          {
            int u = U[e];
            int v = V[e];
            if (((dist[u] != Integer.MAX_VALUE) && ((dist[u] + W[e]) < dist[v]))) {
              dist[v] = (dist[u] + W[e]);
            }
          }
        }
      }
    }
    return dist;
  }

  public static void main(String[] args) {
    int[] U = new int[] {0, 0, 1, 2};
    int[] V = new int[] {0, 0, 1, 1};
    int[] W = new int[] {0, 0, 0, 2};
    BellmanFord prog = new BellmanFord();
    int[] result = prog.shortestPath(U.length, U, V, W, 0);
    System.out.println(Arrays.toString(result));
  }
}