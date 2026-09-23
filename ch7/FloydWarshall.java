// Floyd-Warshall APSP: triple loop on intermediate vertex.

import java.util.*;

public class FloydWarshall {
  public void run(int[][] G) {
    int n = G.length;
    int k = 0;
    while ((k < n)) {
      int i = 0;
      while ((i < n)) {
        int j = 0;
        while ((j < n)) {
          if (((G[i][k] != 2147483647) && (G[k][j] != 2147483647))) {
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
    int[][] G = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    FloydWarshall prog = new FloydWarshall();
    prog.run(G);
    System.out.println(Arrays.deepToString(G));
  }
}