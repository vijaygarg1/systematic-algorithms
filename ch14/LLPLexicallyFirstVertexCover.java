// LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge.

import java.util.*;

public class LLPLexicallyFirstVertexCover {
  int n;
  int[][] adj;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i = 0; i < n; i++) {
      if (isLexMinIncident(i, j, adj, G)) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    G[j] = true;
  }

  public boolean[] LLPLexicallyFirstVertexCover(int[][] adj) {
    this.adj = adj;
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

  public boolean isLexMinIncident(int i, int j, int[][] adj, boolean[] G) {
    if ((((adj[i][j] != 1) || G[i]) || G[j])) {
      return false;
    }
    int n = G.length;
    int x = 0;
    while ((x < n)) {
      int y = (x + 1);
      while ((y < n)) {
        if ((((adj[x][y] == 1) && (!G[x])) && (!G[y]))) {
          if (((((x == i) || (x == j)) || (y == i)) || (y == j))) {
            if ((lexLess(x, y, i, j) && (!((x == i) && (y == j))))) {
              return false;
            }
          }
        }
        y = (y + 1);
      }
      x = (x + 1);
    }
    return true;
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
    LLPLexicallyFirstVertexCover prog = new LLPLexicallyFirstVertexCover();
    boolean[] result = prog.LLPLexicallyFirstVertexCover(adj);
    System.out.println(Arrays.toString(result));
  }
}