// LLP-WeightedVertexCover: primal-dual 2-approximation for weighted
// vertex cover, matching bxx-approximate.tex Algorithm
// LLP-WeightedVertexCover (fig:llp-wvc). The lattice is indexed by EDGES,
// as the book's "G: array[E] of real" and "Forbidden(e)" say: G[e] is the
// price on edge e. A vertex v is tight when its incident prices saturate

import java.util.*;

public class LLPWeightedVertexCover {
  int n;
  int[] eu;
  int[] ev;
  double[] w;
  int nv;
  int m;
  double[] G;
  double[] price;
  int[] minSlackEdge;
  int[] edeg;
  int[] efill;
  int[][] inc;
  boolean fresh;
  int e;

  private boolean _forbidden0(int e) {
    if (!(slackMin(e))) return false;
    return true;
  }

  private void _advance0() {
    double step = Math.min((w[eu[e]] - price[eu[e]]), (w[ev[e]] - price[ev[e]]));
    G[e] = (G[e] + step);
    price[eu[e]] = (price[eu[e]] + step);
    price[ev[e]] = (price[ev[e]] + step);
    fresh = false;
  }

  public boolean[] LLPWeightedVertexCover(int[] eu, int[] ev, double[] w) {
    this.eu = eu;
    this.ev = ev;
    this.w = w;
    this.n = eu.length;
    this.nv = w.length;
    this.m = eu.length;
    this.G = new double[m];
    this.price = new double[nv];
    this.minSlackEdge = new int[nv];
    this.edeg = new int[nv];
    this.efill = new int[nv];
    this.inc = new int[nv][];
    this.fresh = false;
    for (int e = 0; e < m; e++) {
      {
        edeg[eu[e]] = (edeg[eu[e]] + 1);
        edeg[ev[e]] = (edeg[ev[e]] + 1);
      }
    }
    for (int v = 0; v < nv; v++) {
      inc[v] = new int[edeg[v]];
    }
    for (int e = 0; e < m; e++) {
      {
        inc[eu[e]][efill[eu[e]]] = e;
        efill[eu[e]] = (efill[eu[e]] + 1);
        inc[ev[e]][efill[ev[e]]] = e;
        efill[ev[e]] = (efill[ev[e]] + 1);
      }
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int e = 0; e < n; e++) {
          if (_forbidden0(e)) {
            this.e = e; _advance0();
            changed = true;
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

  public boolean slack(int e) {
    return ((!tight(eu[e])) && (!tight(ev[e])));
  }

  public void refresh() {
    if ((!fresh)) {
      for (int v = 0; v < nv; v++) {
        minSlackEdge[v] = firstSlackEdge(v);
      }
      fresh = true;
    }
  }

  public int firstSlackEdge(int v) {
    int k = 0;
    while ((k < inc[v].length)) {
      if (slack(inc[v][k])) {
        return inc[v][k];
      }
      k = (k + 1);
    }
    return (0 - 1);
  }

  public boolean slackMin(int e) {
    refresh();
    if ((!slack(e))) {
      return false;
    }
    return ((minSlackEdge[eu[e]] == e) && (minSlackEdge[ev[e]] == e));
  }

  public static void main(String[] args) {
    int[] eu = new int[] {0, 0, 1, 2};
    int[] ev = new int[] {0, 0, 1, 1};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0};
    LLPWeightedVertexCover prog = new LLPWeightedVertexCover();
    boolean[] result = prog.LLPWeightedVertexCover(eu, ev, w);
    System.out.println(Arrays.toString(result));
  }
}