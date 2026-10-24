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
//
// The graph is the book's (L, R, E): adj[j] lists the R-neighbours of the
// L-vertex j, and nr is |R|, which cannot be recovered from the lists when
// some R-vertex has no incident edge. Following "every (w,v) in E \ M"
// over a dense |L| x |R| matrix would scan all of R for every dequeued
// L-vertex however few edges exist, and reading such a matrix is already
// Theta(|L| * |R|).

import java.util.*;

public class VertexCoverFromMatching {
  public boolean[] VertexCoverFromMatching(int[][] adj, int nr, int[] matchL) {
    int L = adj.length;
    int R = nr;
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
        int t = 0;
        while ((t < adj[w].length)) {
          int v = adj[w][t];
          if (((matchL[w] != v) && (!inZ[(L + v)]))) {
            inZ[(L + v)] = true;
            Q[tail] = (L + v);
            tail = (tail + 1);
          }
          t = (t + 1);
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
    boolean[] result = prog.VertexCoverFromMatching(adj, adj.length, matchL);
    System.out.println(Arrays.toString(result));
  }
}