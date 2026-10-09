// Floyd-Warshall APSP: triple loop on intermediate vertex.

import java.util.*;

public class FloydWarshall {
  public void FloydWarshall(int[][] G) {
    int n = G.length;
    int k = 0;
    while ((k < n)) {
      int i = 0;
      while ((i < n)) {
        int j = 0;
        while ((j < n)) {
          if (((G[i][k] != Integer.MAX_VALUE) && (G[k][j] != Integer.MAX_VALUE))) {
            if (((G[i][k] + G[k][j]) < G[i][j])) {
              G[i][j] = (G[i][k] + G[k][j]);
            }
          }
          j = (j + 1);
        }
        i = (i + 1);
      }
      k = (k + 1);
    }
  }

  public static void main(String[] args) {
    int[][] G = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    FloydWarshall prog = new FloydWarshall();
    prog.FloydWarshall(G);
    System.out.println(Arrays.deepToString(G));
  }
}