// LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge.

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
    int k = 0;
    while ((k < adj[u].length)) {
      if ((!G[adj[u][k]])) {
        return adj[u][k];
      }
      k = (k + 1);
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