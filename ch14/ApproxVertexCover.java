// 2-approximation vertex cover: greedily pick both endpoints of an
// uncovered edge, matching bxx-approximate.tex Algorithm
// ApproxVertexCover. The book's input is the edge set E, and the loop
// walks it once, which is what makes the algorithm O(|E|).

import java.util.*;

public class ApproxVertexCover {
  public boolean[] ApproxVertexCover(int[] eu, int[] ev, int n) {
    boolean[] C = new boolean[n];
    int m = eu.length;
    int e = 0;
    while ((e < m)) {
      int u = eu[e];
      int v = ev[e];
      if (((!C[u]) && (!C[v]))) {
        C[u] = true;
        C[v] = true;
      }
      e = (e + 1);
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