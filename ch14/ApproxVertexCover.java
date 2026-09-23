// 2-approximation vertex cover: greedily pick both endpoints of an uncovered edge.

import java.util.*;

public class ApproxVertexCover {
  public boolean[] ApproxVertexCover(int[][] adj) {
    int n = adj.length;
    boolean[] C = new boolean[n];
    boolean[] removed = new boolean[n];
    boolean done = false;
    while ((!done)) {
      done = true;
      int u = 0;
      while ((u < n)) {
        if ((!removed[u])) {
          int v = (u + 1);
          while ((v < n)) {
            if (((!removed[v]) && (adj[u][v] == 1))) {
              C[u] = true;
              C[v] = true;
              removed[u] = true;
              removed[v] = true;
              done = false;
              v = n;
            } else {
              v = (v + 1);
            }
          }
        }
        u = (u + 1);
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    ApproxVertexCover prog = new ApproxVertexCover();
    boolean[] result = prog.ApproxVertexCover(adj);
    System.out.println(Arrays.toString(result));
  }
}