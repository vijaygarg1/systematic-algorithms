// LLP-Prim: each non-root vertex is fixed when the global cross-cut is its
// lightest edge, matching bxx-mst-llp-prim.tex Algorithm LLP-Prim
// (fig:alg-prim-imp).
//
// The graph is a weighted adjacency list: adj[j] holds the neighbours of j
// and the parallel aw[j] their edge weights, so the weight of
// (j, adj[j][t]) is aw[j][t]. No n x n matrix is involved.
//
// key[j] is the book's min{w[i,j] | (i,j) in E'}: the lightest edge from a
// fixed vertex into j, infinity while j has no fixed neighbour. It is
// maintained by the advance -- fixing j relaxes its neighbours -- rather
// than recomputed, so the guard reads it in O(1).
//
// The global minimality the book's forbidden condition demands,
// "w[i,j] = min{w[k,l] | (k,l) in E'}", is not tested here:
// setContainer(PriorityQueue, key[j], min) holds the candidate indices in a
// min-heap keyed by key, so the index offered to the guard already has the
// smallest cross-cut weight and all that remains to check is that it is
// unfixed and reachable. That is the same division of labour as
// LLP-Dijkstra, and as the classical Prim of Algorithm Prim whose heap of
// (v, c) pairs supplies the vertex while `if not fixed[v]` supplies the
// rest. Lowering key[k] in the advance pushes k back with its new key; the
// stale entry is discarded when it surfaces and the guard rejects it.
//
// The earlier version computed the global minimum directly, with
// globalMinCrossCut() calling minCrossCut() for every vertex and each of
// those scanning a dense W -- O(n^2) per guard evaluation, so a sweep cost
// O(n^3) and the program could not reach the O(m log n) of the chapter's
// summary table. It also needed propagateFixed() and a precondition the
// caller had to establish, because fixed[] arrived as a parameter and the
// lightest-edge initialization leaves vertices that ought to be fixed
// looking free. Neither is needed now: j is fixed only when it is attached
// to an already-fixed vertex, so "following the chosen edges from j reaches
// the root" holds by construction.

import java.util.*;

public class LLPPrim {
  int n;
  int[][] adj;
  double[][] aw;
  int root;
  double[] C;
  double[] key;
  int[] parent;
  boolean[] fixed;
  int j;
  java.util.PriorityQueue<double[]> _container;

  private boolean _forbidden0(int j) {
    if (fixed[j]) return false;
    if (!((key[j] < Double.POSITIVE_INFINITY))) return false;
    return true;
  }

  private void _advance0() {
    fixed[j] = true;
    C[j] = key[j];
    int t = 0;
    while ((t < adj[j].length)) {
      int k = adj[j][t];
      if (((!fixed[k]) && (aw[j][t] < key[k]))) {
        key[k] = aw[j][t];
        if (_container != null) _container.add(new double[]{priority(k), k});
        parent[k] = j;
      }
      t = (t + 1);
    }
  }

  private double priority(int j) {
    return key[j];
  }

  public double[] LLPPrim(int[][] adj, double[][] aw, int root) {
    this.adj = adj;
    this.aw = aw;
    this.root = root;
    this.n = adj.length;
    this.C = new double[n];
    this.key = new double[n];
    this.parent = new int[n];
    this.fixed = new boolean[n];
    for (int v = 0; v < n; v++) {
      {
        key[v] = Double.POSITIVE_INFINITY;
        parent[v] = (0 - 1);
      }
    }
    key[root] = 0;
    java.util.PriorityQueue<double[]> _C = new java.util.PriorityQueue<>(
        (a, b) -> Double.compare(a[0], b[0]));
    this._container = _C;
    for (int j = 0; j < n; j++) _C.add(new double[]{priority(j), j});
    while (!_C.isEmpty()) {
      int j = (int) _C.poll()[1];
      if (!_forbidden0(j)) continue;
      this.j = j; _advance0();
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    double[][] aw = new double[][] {{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0}, {9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0}, {17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0}, {25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0}, {33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0}, {41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0}, {49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0}, {57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0}};
    LLPPrim prog = new LLPPrim();
    double[] result = prog.LLPPrim(adj, aw, 0);
    System.out.println(Arrays.toString(result));
  }
}