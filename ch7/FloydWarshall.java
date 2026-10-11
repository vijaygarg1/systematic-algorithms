// Floyd-Warshall APSP: triple loop on intermediate vertex, matching the
// book's three nested \For loops (bxx-shortestPath.tex, alg:floyd-warshall).

import java.util.*;

public class FloydWarshall {
  public void FloydWarshall(int[][] G) {
    int n = G.length;
    for (int k = 0; k < n; k++) {
      {
        for (int i = 0; i < n; i++) {
          {
            for (int j = 0; j < n; j++) {
              {
                if (((G[i][k] != Integer.MAX_VALUE) && (G[k][j] != Integer.MAX_VALUE))) {
                  if (((G[i][k] + G[k][j]) < G[i][j])) {
                    G[i][j] = (G[i][k] + G[k][j]);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[][] G = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    FloydWarshall prog = new FloydWarshall();
    prog.FloydWarshall(G);
    System.out.println(Arrays.deepToString(G));
  }
}