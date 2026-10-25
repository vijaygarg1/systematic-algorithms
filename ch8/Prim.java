// Classical Prim MST (bxx-mst.tex, algo:prim): d[v] is the cost of adding
// v to the fragment, parent[v] the fragment vertex responsible for it,
// fixed[v] marks vertices already in the fragment. Same control structure
// as Dijkstra (bxx-shortestPath.tex's "Comparison with Dijkstra's
// algorithm" paragraph): a heap<int> keyed by d finds the minimum
// non-fixed vertex, with lazy deletion for stale entries. Since the heap
// only ever holds vertices whose d has become finite, the book's "if
// d[v] = infinity, return null" (no spanning tree) becomes "if the heap
// empties before every vertex is fixed, return null" here.
//
// The graph is a weighted adjacency list: adj[v] holds the neighbours of
// v and the parallel aw[v] holds their edge weights, so the weight of
// (v, adj[v][t]) is aw[v][t]. There is no n x n matrix anywhere, which is
// what the book's \KwIn now asks for -- the whole structure is O(n + m).
//
// The relaxation loop walks adj[v], which is the book's
// "forall v': not fixed[v'] and (v,v') in E". With the heap supplying the
// minimum this is the O(m log n) variant of the chapter's summary table,
// not the O(n^2) array-scan variant of the surrounding text
// (bxx-mst.tex:1009) -- that one finds the minimum by scanning d[] rather
// than with a heap. Scanning all n vertices and reading weights from a
// dense matrix would cost Theta(n^2) however few edges the graph has, and
// so could not meet O(m log n) on a sparse graph.

import java.util.*;

public class Prim {
  public int[] Prim(int[][] adj, int[][] aw) {
    int n = adj.length;
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
        for (int t = 0; t < adj[v].length; t++) {
          {
            int k = adj[v][t];
            if (((!fixed[k]) && (aw[v][t] < d[k]))) {
              d[k] = aw[v][t];
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
    int[][] adj = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] aw = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    Prim prog = new Prim();
    int[] result = prog.Prim(adj, aw);
    System.out.println(Arrays.toString(result));
  }
}