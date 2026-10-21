// Classical Prim MST (bxx-mst.tex, algo:prim): d[v] is the cost of adding
// v to the fragment, parent[v] the fragment vertex responsible for it,
// fixed[v] marks vertices already in the fragment. Same control structure
// as Dijkstra (bxx-shortestPath.tex's "Comparison with Dijkstra's
// algorithm" paragraph): a heap<int> keyed by d finds the minimum
// non-fixed vertex, with lazy deletion for stale entries. Since the heap
// only ever holds vertices whose d has become finite, the book's "if
// d[v] = infinity, return null" (no spanning tree) becomes "if the heap
// empties before every vertex is fixed, return null" here.

import java.util.*;

public class Prim {
  public int[] mst(int[][] w) {
    int n = w.length;
    int[] d = new int[n];
    int[] parent = new int[n];
    boolean[] fixed = new boolean[n];
    for (int i = 0; i < n; i++) {
      {
        d[i] = Integer.MAX_VALUE;
        parent[i] = (-1);
      }
    }
    d[0] = 0;
    java.util.PriorityQueue<int[]> H = new java.util.PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    H.add(new int[]{d[0], 0});
    int count = 0;
    while ((count < n)) {
      if (H.isEmpty()) {
        return null;
      }
      int v = H.peek()[1];
      H.poll();
      if ((!fixed[v])) {
        fixed[v] = true;
        count = (count + 1);
        for (int k = 0; k < n; k++) {
          {
            if ((((!fixed[k]) && (w[v][k] != Integer.MAX_VALUE)) && (w[v][k] < d[k]))) {
              d[k] = w[v][k];
              parent[k] = v;
              H.add(new int[]{d[k], k});
            }
          }
        }
      }
    }
    return parent;
  }

  public static void main(String[] args) {
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    Prim prog = new Prim();
    int[] result = prog.mst(w);
    System.out.println(Arrays.toString(result));
  }
}