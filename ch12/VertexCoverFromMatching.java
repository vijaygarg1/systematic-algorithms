// Koenig's theorem: minimum vertex cover of size |M| from a maximum
// bipartite matching, via alternating reachability. Matches
// bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set
// of vertices reachable from an unmatched L-vertex by an alternating path
// (non-matching edge, then matching edge, ...); C := (L \ Z) union (R
// intersect Z). The book treats the matching M as given directly as a
// partner lookup; here it is given as matchL[j] = R-partner of L-vertex
// j (or -1 if unmatched), with matchR (the same matching read from the
// R side -- the only direction the book's "partner[w]" needs, since w is
// always an R-vertex at that point in the algorithm) derived from it.

import java.util.*;

public class VertexCoverFromMatching {
  public boolean[] VertexCoverFromMatching(int[][] adj, int[] matchL) {
    int L = adj.length;
    int R = adj[0].length;
    int[] matchR = new int[R];
    for (int v = 0; v < R; v++) {
      matchR[v] = (0 - 1);
    }
    for (int j = 0; j < L; j++) {
      if ((matchL[j] != (0 - 1))) {
        matchR[matchL[j]] = j;
      }
    }
    boolean[] inZ = new boolean[(L + R)];
    int[] Q = new int[(L + R)];
    int head = 0;
    int tail = 0;
    for (int j = 0; j < L; j++) {
      if ((matchL[j] == (0 - 1))) {
        inZ[j] = true;
        Q[tail] = j;
        tail = (tail + 1);
      }
    }
    while ((head < tail)) {
      int w = Q[head];
      head = (head + 1);
      if ((w < L)) {
        int v = 0;
        while ((v < R)) {
          if ((((adj[w][v] == 1) && (matchL[w] != v)) && (!inZ[(L + v)]))) {
            inZ[(L + v)] = true;
            Q[tail] = (L + v);
            tail = (tail + 1);
          }
          v = (v + 1);
        }
      } else {
        int r = (w - L);
        if (((matchR[r] != (0 - 1)) && (!inZ[matchR[r]]))) {
          inZ[matchR[r]] = true;
          Q[tail] = matchR[r];
          tail = (tail + 1);
        }
      }
    }
    boolean[] C = new boolean[(L + R)];
    for (int j = 0; j < L; j++) {
      C[j] = (!inZ[j]);
    }
    for (int v = 0; v < R; v++) {
      C[(L + v)] = inZ[(L + v)];
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[] matchL = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    VertexCoverFromMatching prog = new VertexCoverFromMatching();
    boolean[] result = prog.VertexCoverFromMatching(adj, matchL);
    System.out.println(Arrays.toString(result));
  }
}