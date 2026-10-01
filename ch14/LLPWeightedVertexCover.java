// LLP-WeightedVertexCover: primal-dual 2-approximation for weighted vertex
// cover, matching bxx-approximate.tex Algorithm LLP-WeightedVertexCover.
// G[{u,v}] is the price on edge {u,v}; a vertex v is tight when its
// incident prices saturate w[v]; an edge is slack when neither endpoint
// is tight. To keep this a well-formed LLP computation, only a
// lex-minimal slack edge (one no larger, in edge order, than any slack
// edge sharing a vertex with it) may advance -- distinct lex-minimal
// slack edges are pairwise non-adjacent, so advancing one never disturbs
// another. Each advance raises its edge's price by the smaller of its
// two endpoints' remaining budgets, tightening at least one endpoint.
// Sequentially, repeatedly advancing the single globally lex-least
// slack edge is a valid schedule (it is trivially lex-minimal among its
// neighbors), so that is what this program does. Output: C[v] := tight(v).
//
// Earlier version of this file (kept for reference, not used): raised
// every currently-slack edge by one common uniform step per round
// (Vazirani-style), rather than the book's per-edge, lex-minimal-only
// step. Left in a comment below rather than deleted.
//
// class LLPWeightedVertexCover {
// void LLPWeightedVertexCover(int[][] adj, double[] w) {
// int nv = w.length;
// double[][] G = new double[nv][nv];
// boolean changed = true;
// while (changed) {
// changed = false;
// double r = computeStep(adj, w, G, nv);
// if (r <= 0.0) { changed = false; };
// int u = 0;
// while (u < nv) {
// int v = u + 1;
// while (v < nv) {
// if (adj[u][v] == 1 && !tight(u, adj, w, G, nv) && !tight(v, adj, w, G, nv)) {
// G[u][v] = G[u][v] + r;
// G[v][u] = G[v][u] + r;
// changed = true;
// };
// v = v + 1;
// };
// u = u + 1;
// }
// };
// }
//
// boolean tight(int v, int[][] adj, double[] w, double[][] G, int nv) {
// double sum = 0.0;
// int u = 0;
// while (u < nv) {
// if (adj[u][v] == 1) { sum = sum + G[u][v]; };
// u = u + 1;
// };
// return sum >= w[v];
// }
//
// double computeStep(int[][] adj, double[] w, double[][] G, int nv) {
// double r = infinity;
// int v = 0;
// while (v < nv) {
// int slackDeg = 0;
// int u = 0;
// while (u < nv) {
// if (adj[u][v] == 1 && !tight(u, adj, w, G, nv) && !tight(v, adj, w, G, nv)) {
// slackDeg = slackDeg + 1;
// };
// u = u + 1;
// };
// if (slackDeg > 0) {
// double sum = 0.0;
// u = 0;
// while (u < nv) {
// if (adj[u][v] == 1) { sum = sum + G[u][v]; };
// u = u + 1;
// };
// double ratio = (w[v] - sum) / slackDeg;
// if (ratio < r) { r = ratio; };
// };
// v = v + 1;
// };
// return r;
// }
// }

import java.util.*;

public class LLPWeightedVertexCover {
  public boolean[] LLPWeightedVertexCover(int[][] adj, double[] w) {
    int nv = w.length;
    double[][] G = new double[nv][nv];
    boolean done = false;
    while ((!done)) {
      int su = (0 - 1);
      int sv = (0 - 1);
      int u = 0;
      while (((u < nv) && (su == (0 - 1)))) {
        if ((!tight(u, adj, w, G, nv))) {
          int v = (u + 1);
          while (((v < nv) && (su == (0 - 1)))) {
            if (((adj[u][v] == 1) && (!tight(v, adj, w, G, nv)))) {
              su = u;
              sv = v;
            }
            v = (v + 1);
          }
        }
        u = (u + 1);
      }
      if ((su == (0 - 1))) {
        done = true;
      } else {
        double step = Math.min((w[su] - price(su, adj, G, nv)), (w[sv] - price(sv, adj, G, nv)));
        G[su][sv] = (G[su][sv] + step);
        G[sv][su] = G[su][sv];
      }
    }
    boolean[] C = new boolean[nv];
    int i = 0;
    while ((i < nv)) {
      C[i] = tight(i, adj, w, G, nv);
      i = (i + 1);
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

  public static void main(String[] args) {
    int[][] adj = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0};
    LLPWeightedVertexCover prog = new LLPWeightedVertexCover();
    boolean[] result = prog.LLPWeightedVertexCover(adj, w);
    System.out.println(Arrays.toString(result));
  }
}