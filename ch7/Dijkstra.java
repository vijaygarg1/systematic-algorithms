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
//
// The graph is a weighted adjacency list: adj[j] holds the out-neighbours
// of j and the parallel aw[j] holds their edge weights, so the weight of
// (j, adj[j][t]) is aw[j][t]. There is no n x n matrix anywhere, so the
// whole structure is O(n + m) rather than Theta(n^2).
//
// The relaxation loop walks adj[j], which is the book's
// "forall k: not fixed[k] and (j,k) in E". That is what makes the
// algorithm O(m log n): each edge is relaxed once and contributes at most
// one heap push. Scanning all n vertices instead -- reading weights out
// of a dense matrix -- costs Theta(n^2) however few edges the graph has,
// and so cannot meet the book's bound on a sparse graph.

import java.util.*;

public class Dijkstra {
  public int[] shortestPath(int[][] adj, int[][] aw, int s) {
    int n = adj.length;
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
        int t = 0;
        while ((t < adj[j].length)) {
          int k = adj[j][t];
          if (((!fixed[k]) && ((dist[j] + aw[j][t]) < dist[k]))) {
            dist[k] = (dist[j] + aw[j][t]);
            H.add(new int[]{dist[k], k});
          }
          t = (t + 1);
        }
      }
    }
    return dist;
  }

  public static void main(String[] args) {
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] aw = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    Dijkstra prog = new Dijkstra();
    int[] result = prog.shortestPath(adj, aw, 0);
    System.out.println(Arrays.toString(result));
  }
}