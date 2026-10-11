// LLP-WeightedVertexCover: primal-dual 2-approximation for weighted
// vertex cover, matching bxx-approximate.tex Algorithm
// LLP-WeightedVertexCover (fig:llp-wvc). G[u][v] is the price on edge
// {u,v}; a vertex v is tight when its incident prices saturate w[v]; an
// edge is slack when neither endpoint is tight; slackMin(u,v) further
// requires {u,v} to be lex-minimal among every slack edge sharing an
// endpoint with it -- the same lex-minimality check as
// LLPLexicallyFirstVertexCover, just over slack edges instead of
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
    if (!(slackMin(u, v, adj, w, G))) return false;
    return true;
  }

  private void _advance0() {
    double step = Math.min((w[u] - price(u, adj, G, nv)), (w[v] - price(v, adj, G, nv)));
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
      C[i] = tight(i, adj, w, G, nv);
    }
    return C;
  }

  public double price(int v, int[][] adj, double[][] G, int nv) {
    double sum = 0.0;
    int u = 0;
    while ((u < nv)) {
      if ((adj[u][v] == 1)) {
        sum = (sum + G[u][v]);
      }
      u = (u + 1);
    }
    return sum;
  }

  public boolean tight(int v, int[][] adj, double[] w, double[][] G, int nv) {
    return (price(v, adj, G, nv) >= w[v]);
  }

  public boolean slack(int u, int v, int[][] adj, double[] w, double[][] G, int nv) {
    return ((!tight(u, adj, w, G, nv)) && (!tight(v, adj, w, G, nv)));
  }

  public boolean slackMin(int u, int v, int[][] adj, double[] w, double[][] G) {
    int nv = w.length;
    if (((adj[u][v] != 1) || (!slack(u, v, adj, w, G, nv)))) {
      return false;
    }
    int x = 0;
    while ((x < nv)) {
      int y = (x + 1);
      while ((y < nv)) {
        if (((adj[x][y] == 1) && slack(x, y, adj, w, G, nv))) {
          if (((((x == u) || (x == v)) || (y == u)) || (y == v))) {
            if ((lexLess(x, y, u, v) && (!((x == u) && (y == v))))) {
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