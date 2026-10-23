// LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge.
//
// Edges are ordered by (min(u,v), max(u,v)), and the lex-least uncovered edge
// incident to a vertex u is always {u, minNbr(u)}, where minNbr(u) is u's
// smallest uncovered neighbour: a neighbour y < u contributes the key (y, u)
// and a neighbour y > u the key (u, y), so in both cases the smallest index
// wins. An edge is therefore lex-minimal among the edges touching it exactly
// when it is the lex-least edge at BOTH of its endpoints, which is what
// isLexMin below tests with two O(n) scans. Testing it directly -- "no smaller
// uncovered edge touches i or j" -- costs an O(n^2) scan over all edge pairs
// per candidate, so the predicate alone dominated the whole run.

import java.util.*;

public class LLPLexMatchVertexCover {
  int n;
  int[][] adj;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(isLexMin(j))) return false;
    return true;
  }

  private void advance() {
    int i = minNbr(j);
    G[j] = true;
    G[i] = true;
  }

  public boolean[] LLPLexMatchVertexCover(int[][] adj) {
    this.adj = adj;
    this.n = adj.length;
    this.G = new boolean[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean isLexMin(int j) {
    if (G[j]) {
      return false;
    }
    int i = minNbr(j);
    if ((i < 0)) {
      return false;
    }
    return (minNbr(i) == j);
  }

  public int minNbr(int u) {
    int y = 0;
    while ((y < n)) {
      if (((adj[u][y] == 1) && (!G[y]))) {
        return y;
      }
      y = (y + 1);
    }
    return (0 - 1);
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPLexMatchVertexCover prog = new LLPLexMatchVertexCover();
    boolean[] result = prog.LLPLexMatchVertexCover(adj);
    System.out.println(Arrays.toString(result));
  }
}