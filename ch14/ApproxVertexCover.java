// 2-approximation vertex cover: greedily pick both endpoints of an
// uncovered edge, matching bxx-approximate.tex Algorithm
// ApproxVertexCover. The book's input is the edge set E, and the loop
// walks it once, which is what makes the algorithm O(|E|).
//
// The book's "remove all edges incident to u or v from E'" needs no
// bookkeeping: an edge is still in E' exactly when neither of its
// endpoints has been taken into C, so the test below is that membership
// test. Taking the edge set rather than an n x n adjacency matrix is
// what keeps this O(|E|) -- a matrix forces every pass to scan all n^2
// cells no matter how few edges exist, and reading the input alone would
// already be Theta(n^2).

import java.util.*;

public class ApproxVertexCover {
  public boolean[] ApproxVertexCover(int[] eu, int[] ev, int n) {
    boolean[] C = new boolean[n];
    int m = eu.length;
    for (int e = 0; e < m; e++) {
      {
        int u = eu[e];
        int v = ev[e];
        if (((!C[u]) && (!C[v]))) {
          C[u] = true;
          C[v] = true;
        }
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[] eu = new int[] {0, 0, 1, 2};
    int[] ev = new int[] {0, 0, 1, 1};
    ApproxVertexCover prog = new ApproxVertexCover();
    boolean[] result = prog.ApproxVertexCover(eu, ev, eu.length);
    System.out.println(Arrays.toString(result));
  }
}