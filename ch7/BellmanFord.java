// Classical Bellman-Ford: n-1 relaxation passes over every edge.

import java.util.*;

public class BellmanFord {
  public int[] shortestPath(int n, int[] U, int[] V, int[] W, int s) {
    int[] dist = new int[n];
    for (int i = 0; i < n; i++) {
      dist[i] = 2147483647;
    }
    dist[s] = 0;
    int k = 1;
    while ((k < n)) {
      int e = 0;
      while ((e < U.length)) {
        int u = U[e];
        int v = V[e];
        if (((dist[u] != 2147483647) && ((dist[u] + W[e]) < dist[v]))) {
          dist[v] = (dist[u] + W[e]);
        }
        e = (e + 1);
      }
      k = (k + 1);
    }
    return dist;
  }

  public static void main(String[] args) {
    int[] U = new int[] {1, 4, 7};
    int[] V = new int[] {2, 3, 5, 8};
    int[] W = new int[] {6, 9};
    BellmanFord prog = new BellmanFord();
    int[] result = prog.shortestPath(U.length, U, V, W, 0);
    System.out.println(Arrays.toString(result));
  }
}