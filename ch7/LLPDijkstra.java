// LLP-Dijkstra: forbidden when j is not yet fixed and its distance is
// finite; the advance fixes j and relaxes every edge out of it. The
// setContainer line gives the LLP loop a priority queue keyed on G[j],
// so the forbidden index chosen is always the closest unfixed vertex --
// which is exactly Dijkstra's greedy choice.
//
// The book writes the weight as w(i,j), with parentheses, to say that it
// comes from the edge and not from an n x n array -- an array would cost
// Theta(n^2) to build before Dijkstra's own O(m log n) could start. So the
// edges arrive as two parallel ragged arrays: adj[j] lists j's successors
// and aw[j][t] is the weight of the edge j -> adj[j][t]. Both are O(m).
// Dijkstra.llp and LLPJohnson.llp use the same pair.

import java.util.*;

public class LLPDijkstra {
  int n;
  int[][] adj;
  double[][] aw;
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
    for (int t = 0; t < adj[j].length; t++) {
      {
        int k = adj[j][t];
        if ((!fixed[k])) {
          double cand = (G[j] + aw[j][t]);
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

  public double[] LLPDijkstra(int[][] adj, double[][] aw) {
    this.adj = adj;
    this.aw = aw;
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
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    double[][] aw = new double[][] {{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0}, {9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0}, {17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0}, {25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0}, {33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0}, {41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0}, {49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0}, {57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0}};
    LLPDijkstra prog = new LLPDijkstra();
    double[] result = prog.LLPDijkstra(adj, aw);
    System.out.println(Arrays.toString(result));
  }
}