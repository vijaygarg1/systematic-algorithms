// LLP-WeightedVertexCover: primal-dual 2-approximation for weighted
// vertex cover, matching bxx-approximate.tex Algorithm
// LLP-WeightedVertexCover (fig:llp-wvc). G[u][v] is the price on edge
// {u,v}; a vertex v is tight when its incident prices saturate w[v]; an
// edge is slack when neither endpoint is tight; slackMin(u,v) further
// requires {u,v} to be lex-minimal among every slack edge sharing an
// endpoint with it -- the same lex-minimality check as
// LLPLexMatchVertexCover, just over slack edges instead of
// uncovered ones. Advancing raises the edge's price by the smaller of
// its two endpoints' remaining budgets, tightening at least one.

import java.util.*;

public class LLPWeightedVertexCover {
  int n;
  int[][] adj;
  double[] w;
  int nv;
  double[][] G;
  int u;
  int v;

  private boolean _forbidden0(int u, int v) {
    if (!((u < v))) return false;
    if (!(slackMin(u, v))) return false;
    return true;
  }

  private void _advance0() {
    double step = Math.min((w[u] - price(u)), (w[v] - price(v)));
    G[u][v] = (G[u][v] + step);
    G[v][u] = G[u][v];
  }

  public boolean[] LLPWeightedVertexCover(int[][] adj, double[] w) {
    this.adj = adj;
    this.w = w;
    this.n = w.length;
    this.nv = w.length;
    this.G = new double[nv][nv];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int u = 0; u < n; u++) {
          for (int v = 0; v < n; v++) {
            if (_forbidden0(u, v)) {
              this.u = u; this.v = v; _advance0();
              changed = true;
            }
          }
        }
      }
    }
    boolean[] C = new boolean[nv];
    for (int i = 0; i < nv; i++) {
      C[i] = tight(i);
    }
    return C;
  }

  public double price(int v) {
    double total = 0.0;
    for (int u = 0; u < nv; u++) {
      {
        if ((adj[u][v] == 1)) {
          total = (total + G[u][v]);
        }
      }
    }
    return total;
  }

  public boolean tight(int v) {
    return (price(v) >= w[v]);
  }

  public boolean slack(int u, int v) {
    return ((!tight(u)) && (!tight(v)));
  }

  public boolean slackMin(int u, int v) {
    boolean t1 = false;
    for (int x = 0; x < nv; x++) {
      boolean t2 = false;
      for (int y = (x + 1); y < nv; y++) {
        if ((((((adj[x][y] == 1) && ((((x == u) || (x == v)) || (y == u)) || (y == v))) && slack(x, y)) && lexLess(x, y, u, v)) && (!((x == u) && (y == v))))) { t2 = true; break; }
      }
      if (t2) { t1 = true; break; }
    }
    return (((adj[u][v] == 1) && slack(u, v)) && (!t1));
  }

  public boolean lexLess(int a, int b, int c, int d) {
    if ((a < c)) {
      return true;
    }
    if ((a > c)) {
      return false;
    }
    return (b < d);
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPWeightedVertexCover prog = new LLPWeightedVertexCover();
    boolean[] result = prog.LLPWeightedVertexCover(adj, w);
    System.out.println(Arrays.toString(result));
  }
}