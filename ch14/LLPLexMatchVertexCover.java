// LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge.

import java.util.*;

public class LLPLexMatchVertexCover {
  int n;
  int[][] adj;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i = 0; i < n; i++) {
      if (isLexMinIncident(i, j)) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    G[j] = true;
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

  public boolean isLexMinIncident(int i, int j) {
    boolean t2 = false;
    for (int x = 0; x < n; x++) {
      boolean t3 = false;
      for (int y = (x + 1); y < n; y++) {
        if (((((((adj[x][y] == 1) && ((((x == i) || (x == j)) || (y == i)) || (y == j))) && (!G[x])) && (!G[y])) && lexLess(x, y, i, j)) && (!((x == i) && (y == j))))) { t3 = true; break; }
      }
      if (t3) { t2 = true; break; }
    }
    return ((((adj[i][j] == 1) && (!G[i])) && (!G[j])) && (!t2));
  }

  public boolean lexLess(int a, int b, int c, int d) {
    int amin = a;
    int amax = b;
    if ((b < a)) {
      amin = b;
      amax = a;
    }
    int cmin = c;
    int cmax = d;
    if ((d < c)) {
      cmin = d;
      cmax = c;
    }
    if ((amin < cmin)) {
      return true;
    }
    if ((amin > cmin)) {
      return false;
    }
    return (amax < cmax);
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPLexMatchVertexCover prog = new LLPLexMatchVertexCover();
    boolean[] result = prog.LLPLexMatchVertexCover(adj);
    System.out.println(Arrays.toString(result));
  }
}