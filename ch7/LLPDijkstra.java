// LLPDijkstra: Dijkstra as an LLP schedule with a min-heap of non-fixed
// vertices keyed by d. The graph is supplied as adjacency lists
// adj[j] and weight array w[j] --- omitted here for clarity; a double
// compiler front-end would flesh these out.

import java.util.*;

public class LLPDijkstra {
  int n;
  int[] dIn;
  int[] d;
  boolean[] fixed;
  int j;
  PriorityQueue<Integer> forbiddenHeap;

  private boolean forbidden(int j) {
    if (fixed[j]) return false;
    return true;
  }

  private void advance() {
    fixed[j] = true;
  }

  private double priority(int j) {
    return d[j];
  }

  public int[] LLPDijkstra(int[] dIn) {
    this.dIn = dIn;
    this.n = dIn.length;
    this.d = new int[n];
    for (int i = 0; i < n; i++) this.d[i] = Integer.MAX_VALUE;
    this.fixed = new boolean[n];
    forbiddenHeap = new PriorityQueue<>(
      (a, b) -> Double.compare(priority(a), priority(b)));
    for (int j = 0; j < n; j++) forbiddenHeap.add(j);
    while (!forbiddenHeap.isEmpty()) {
      int j = forbiddenHeap.poll();
      if (forbidden(j)) { this.j = j; advance(); }
    }
    return d;
  }

  public static void main(String[] args) {
    int[] dIn = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPDijkstra prog = new LLPDijkstra();
    int[] result = prog.LLPDijkstra(dIn);
    System.out.println(Arrays.toString(result));
  }
}