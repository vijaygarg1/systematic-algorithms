// König's-theorem construction of a vertex cover of size |M| from a max matching.

import java.util.*;

public class ParVertexCoverFromMatching {
  public boolean[] ParVertexCoverFromMatching(int[][] adj, int[] matchL) {
    int L = adj.length;
    int R = adj[0].length;
    boolean[] C = new boolean[(L + R)];
    int[] partner = new int[(L + R)];
    int i = 0;
    while ((i < (L + R))) {
      partner[i] = (0 - 1);
      i = (i + 1);
    }
    int u = 0;
    while ((u < L)) {
      int v = matchL[u];
      if ((v != (0 - 1))) {
        C[u] = true;
        partner[u] = (L + v);
        partner[(L + v)] = u;
      }
      u = (u + 1);
    }
    u = 0;
    while ((u < L)) {
      int v = 0;
      while ((v < R)) {
        if ((((adj[u][v] == 1) && (!C[u])) && (!C[(L + v)]))) {
          if ((partner[u] != (0 - 1))) {
            C[partner[u]] = false;
            C[u] = true;
          } else {
            C[partner[(L + v)]] = false;
            C[(L + v)] = true;
          }
        }
        v = (v + 1);
      }
      u = (u + 1);
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] matchL = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    ParVertexCoverFromMatching prog = new ParVertexCoverFromMatching();
    boolean[] result = prog.ParVertexCoverFromMatching(adj, matchL);
    System.out.println(Arrays.toString(result));
  }
}