// König's theorem: minimum vertex cover of size |M| from a maximum
// bipartite matching, via alternating reachability. Matches
// bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set
// of vertices reachable from an unmatched L-vertex by an alternating path
// (non-matching edge, then matching edge, ...); C := (L \ Z) union (R
// intersect Z).

import java.util.*;

public class ParVertexCoverFromMatching {
  public boolean[] ParVertexCoverFromMatching(int[][] adj, int[] matchL) {
    int L = adj.length;
    int R = adj[0].length;
    boolean[] inZ = new boolean[(L + R)];
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
        partner[u] = (L + v);
        partner[(L + v)] = u;
      }
      u = (u + 1);
    }
    int[] Q = new int[(L + R)];
    int head = 0;
    int tail = 0;
    u = 0;
    while ((u < L)) {
      if ((matchL[u] == (0 - 1))) {
        inZ[u] = true;
        Q[tail] = u;
        tail = (tail + 1);
      }
      u = (u + 1);
    }
    while ((head < tail)) {
      int w = Q[head];
      head = (head + 1);
      if ((w < L)) {
        int v = 0;
        while ((v < R)) {
          if ((((adj[w][v] == 1) && (partner[w] != (L + v))) && (!inZ[(L + v)]))) {
            inZ[(L + v)] = true;
            Q[tail] = (L + v);
            tail = (tail + 1);
          }
          v = (v + 1);
        }
      } else {
        if (((partner[w] != (0 - 1)) && (!inZ[partner[w]]))) {
          inZ[partner[w]] = true;
          Q[tail] = partner[w];
          tail = (tail + 1);
        }
      }
    }
    boolean[] C = new boolean[(L + R)];
    u = 0;
    while ((u < L)) {
      C[u] = (!inZ[u]);
      u = (u + 1);
    }
    int v = 0;
    while ((v < R)) {
      C[(L + v)] = inZ[(L + v)];
      v = (v + 1);
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