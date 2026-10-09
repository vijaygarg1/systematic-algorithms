// Classical Dijkstra (bxx-shortestPath.tex, fig:dijk): binary min-heap
// H of (vertex, cost) pairs keyed by cost, with lazy deletion -- a
// vertex may be pushed more than once as its distance improves; stale
// entries are simply skipped on removal via the fixed[] check -- all
// exactly matching the book's H.add()/H.removeMin() algorithm, not a
// linear O(n) scan for the minimum each round. Uses the heap<int>
// builtin (-> java.util.PriorityQueue<int[]>, keyed by cost, carrying
// vertex as the payload) instead of hand-rolled heapPush/heapPop/
// heapify, matching the hand-written Python (heapq) and Rust
// (BinaryHeap) versions of this same program, which already lean on
// their standard library's heap.

import java.util.*;

public class Dijkstra {
  public int[] shortestPath(int[][] w, int s) {
    int n = w.length;
    int INF = Integer.MAX_VALUE;
    int[] dist = new int[n];
    boolean[] fixed = new boolean[n];
    for (int i = 0; i < n; i++) {
      dist[i] = INF;
    }
    dist[s] = 0;
    java.util.PriorityQueue<int[]> H = new java.util.PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    H.add(new int[]{dist[s], s});
    while ((!H.isEmpty())) {
      int j = H.peek()[1];
      H.poll();
      if ((!fixed[j])) {
        fixed[j] = true;
        int k = 0;
        while ((k < n)) {
          if ((((!fixed[k]) && (w[j][k] < INF)) && ((dist[j] + w[j][k]) < dist[k]))) {
            dist[k] = (dist[j] + w[j][k]);
            H.add(new int[]{dist[k], k});
          }
          k = (k + 1);
        }
      }
    }
    return dist;
  }

  public static void main(String[] args) {
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    Dijkstra prog = new Dijkstra();
    int[] result = prog.shortestPath(w, 0);
    System.out.println(Arrays.toString(result));
  }
}