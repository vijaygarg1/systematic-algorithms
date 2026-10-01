// LLP-Dijkstra: Dijkstra's algorithm as an LLP schedule, matching
// bxx-sp-llp-dijkstra.tex Algorithm LLP-Dijkstra. G[j] is the current
// shortest-distance estimate from source vertex 0; forbidden(j) holds
// when j is not yet fixed, is reachable (G[j] < infinity), and has the
// minimum G among all non-fixed vertices; advance fixes j and relaxes
// its outgoing edges.
//
// Earlier version of this file (kept for reference, not used): a
// setMode(PriorityQueue)/setPriority(...) syntax demo whose advance
// rule only set fixed[j] := true and never relaxed any edge -- it
// computed nothing. Left in a comment below rather than deleted.
//
// class LLPDijkstra {
// int[] LLPDijkstra(int[]  dIn) {
// int[]  d = infinity;
// boolean[] fixed = false;
//
// setMode(PriorityQueue);
// setPriority(d[j], min);
//
// // A j is forbidden if not yet fixed and its current d matches the head
// // of the heap. The advance fixes it and relaxes its outgoing edges
// // (the relaxation code would be expanded here in a full front-end).
// forbidden (j) : !fixed[j] =>
// advance : fixed[j] = true;
// return d;
// }
// }

import java.util.*;

public class LLPDijkstra {
  int n;
  int[][] adj;
  double[][] w;
  double[] G;
  boolean[] fixed;
  int j;

  private boolean _forbidden0(int j) {
    if (fixed[j]) return false;
    if (!((G[j] < Integer.MAX_VALUE))) return false;
    if (!((G[j] <= globalMin()))) return false;
    return true;
  }

  private void _advance0() {
    fixed[j] = true;
    for (int k : adj[j]) {
      {
        if ((!fixed[k])) {
          double cand = (G[j] + w[j][k]);
          if ((cand < G[k])) {
            G[k] = cand;
          }
        }
      }
    }
  }

  public double[] LLPDijkstra(int[][] adj, double[][] w) {
    this.adj = adj;
    this.w = w;
    this.n = adj.length;
    this.G = new double[n];
    this.fixed = new boolean[n];
    G[0] = 0.0;
    int i = 1;
    while ((i < n)) {
      G[i] = Integer.MAX_VALUE;
      i = (i + 1);
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public double globalMin() {
    double best = Integer.MAX_VALUE;
    int i = 0;
    while ((i < n)) {
      if (((!fixed[i]) && (G[i] < best))) {
        best = G[i];
      }
      i = (i + 1);
    }
    return best;
  }

  public static void main(String[] args) {
    // Demo harness for LLPDijkstra.
    // Construct with hard-coded inputs and call the entry method.
  }
}