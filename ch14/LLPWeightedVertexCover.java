// LLP-WeightedVertexCover: primal-dual 2-approximation for weighted
// vertex cover, matching bxx-approximate.tex Algorithm
// LLP-WeightedVertexCover (fig:llp-wvc). G[u][v] is the price on edge
// {u,v}; a vertex v is tight when its incident prices saturate w[v]; an
// edge is slack when neither endpoint is tight; slackMin(u,v) further
// requires {u,v} to be lex-minimal among every slack edge sharing an
// endpoint with it. Advancing raises the edge's price by the smaller of
// its two endpoints' remaining budgets, tightening at least one.
//
// Two things keep the predicate cheap. First, price[] is maintained by the
// advance instead of being re-summed on every call, so tight() is O(1)
// rather than an O(nv) sum. Second, the lex-minimality test does not scan
// all edge pairs: the lex-least slack edge at a vertex u is always
// {u, minSlack[u]} where minSlack[u] is u's smallest non-tight neighbour
// (a neighbour y < u contributes the key (y,u) and y > u the key (u,y), so
// the smallest index wins either way), hence {u,v} is lex-minimal among the
// slack edges touching it exactly when it is the lex-least at both ends.
// minSlack[] is rebuilt only after an advance has moved a price.

import java.util.*;

public class LLPWeightedVertexCover {
  int n;
  int[][] adj;
  double[] w;
  int nv;
  double[][] G;
  double[] price;
  int[] minSlack;
  boolean fresh;
  int u;
  int v;

  private boolean _forbidden0(int u, int v) {
    if (!((u < v))) return false;
    if (!(slackMin(u, v))) return false;
    return true;
  }

  private void _advance0() {
    double step = Math.min((w[u] - price[u]), (w[v] - price[v]));
    G[u][v] = (G[u][v] + step);
    G[v][u] = G[u][v];
    price[u] = (price[u] + step);
    price[v] = (price[v] + step);
    fresh = false;
  }

  public boolean[] LLPWeightedVertexCover(int[][] adj, double[] w) {
    this.adj = adj;
    this.w = w;
    this.n = w.length;
    this.nv = w.length;
    this.G = new double[nv][nv];
    this.price = new double[nv];
    this.minSlack = new int[nv];
    this.fresh = false;
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

  public boolean tight(int v) {
    return (price[v] >= w[v]);
  }

  public void refresh() {
    if ((!fresh)) {
      for (int u = 0; u < nv; u++) {
        minSlack[u] = firstSlackNbr(u);
      }
      fresh = true;
    }
  }

  public int firstSlackNbr(int u) {
    int y = 0;
    while ((y < nv)) {
      if (((adj[u][y] == 1) && (!tight(y)))) {
        return y;
      }
      y = (y + 1);
    }
    return (0 - 1);
  }

  public boolean slackMin(int u, int v) {
    if ((adj[u][v] != 1)) {
      return false;
    }
    refresh();
    if ((tight(u) || tight(v))) {
      return false;
    }
    return ((minSlack[u] == v) && (minSlack[v] == u));
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPWeightedVertexCover prog = new LLPWeightedVertexCover();
    boolean[] result = prog.LLPWeightedVertexCover(adj, w);
    System.out.println(Arrays.toString(result));
  }
}