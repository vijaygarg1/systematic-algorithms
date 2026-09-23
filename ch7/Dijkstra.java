// Classical Dijkstra: extract-min frontier vertex, relax outgoing edges.

import java.util.*;

public class Dijkstra {
  public int[] shortestPath(int[][] w, int s) {
    int n = w.length;
    int[] dist = new int[n];
    boolean[] fixed = new boolean[n];
    for (int i = 0; i < n; i++) {
      dist[i] = 2147483647;
    }
    dist[s] = 0;
    int count = 0;
    while ((count < n)) {
      int j = (-1);
      int best = 2147483647;
      int k = 0;
      while ((k < n)) {
        if (((!fixed[k]) && (dist[k] < best))) {
          j = k;
          best = dist[k];
        }
        k = (k + 1);
      }
      if ((j == (-1))) {
        return dist;
      }
      fixed[j] = true;
      count = (count + 1);
      k = 0;
      while ((k < n)) {
        if (((!fixed[k]) && (w[j][k] < 2147483647))) {
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
    int[][] w = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    Dijkstra prog = new Dijkstra();
    int[] result = prog.shortestPath(w, 0);
    System.out.println(Arrays.toString(result));
  }
}