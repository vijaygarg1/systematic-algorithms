// LLP-Dijkstra: a particular LLP schedule of LLP-BellmanFord that fixes
// one vertex per round -- the non-fixed vertex of minimum G[j] -- using
// a priority-queue scheduler instead of relaxing every forbidden index
// concurrently.

import java.util.*;

public class LLPDijkstra {
  int n;
  int[][] adj;
  double[][] w;
  double[] G;
  boolean[] fixed;
  int j;
  PriorityQueue<Integer> forbiddenHeap;

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
    forbiddenHeap = new PriorityQueue<>(
      (a, b) -> Double.compare(priority(a), priority(b)));
    for (int j = 0; j < n; j++) forbiddenHeap.add(j);
    G[0] = 0;
    while (!forbiddenHeap.isEmpty()) {
      int j = forbiddenHeap.poll();
      if (_forbidden0(j)) { this.j = j; _advance0(); }
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