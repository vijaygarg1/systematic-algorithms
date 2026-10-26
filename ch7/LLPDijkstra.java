// LLP-Dijkstra, matching bxx-sp-llp-dijkstra.tex Algorithm LLP-Dijkstra
// (fig:alg-llp-dijk). The lattice points are PAIRS (fixed, G): a vertex is
// settled by flipping fixed[j], and distances only ever decrease. The
// predicate has two clauses -- every discovered vertex is settled, and the
// shortest-path recurrence holds.

import java.util.*;

public class LLPDijkstra {
  int n;
  int[][] adj;
  double[][] w;
  double[] G;
  boolean[] fixed;
  int j;
  java.util.PriorityQueue<double[]> _container;

  private boolean _forbidden0(int j) {
    if (fixed[j]) return false;
    if (!((G[j] < Double.POSITIVE_INFINITY))) return false;
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
            if (_container != null) _container.add(new double[]{priority(k), k});
          }
        }
      }
    }
  }

  private double priority(int j) {
    return G[j];
  }

  public double[] LLPDijkstra(int[][] adj, double[][] w) {
    this.adj = adj;
    this.w = w;
    this.n = adj.length;
    this.G = new double[n];
    for (int i = 0; i < n; i++) this.G[i] = Double.POSITIVE_INFINITY;
    this.fixed = new boolean[n];
    G[0] = 0;
    java.util.PriorityQueue<double[]> _C = new java.util.PriorityQueue<>(
        (a, b) -> Double.compare(a[0], b[0]));
    this._container = _C;
    for (int j = 0; j < n; j++) _C.add(new double[]{priority(j), j});
    while (!_C.isEmpty()) {
      int j = (int) _C.poll()[1];
      if (!_forbidden0(j)) continue;
      this.j = j; _advance0();
    }
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: LLPDijkstra's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}