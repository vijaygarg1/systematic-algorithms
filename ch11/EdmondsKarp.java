// Classical Edmonds-Karp maximum flow: Ford-Fulkerson with the augmenting
// path chosen by BREADTH-first search, so each augmentation uses a path
// with the fewest arcs. That single change is what bounds the number of
// augmentations by O(VE) and makes the running time independent of the
// capacities.
//
// f is the flow matrix; residual(u, v) = c[u][v] - f[u][v] is the spare
// capacity of arc (u,v), and pushing b along (u,v) also subtracts b from
// f[v][u], which is what lets a later path cancel earlier flow. The BFS
// carries its own queue q with head/tail indices and leaves the path in
// parent[], as a chain of predecessors running back from t to s.

import java.util.*;

public class EdmondsKarp {
  public int[][] maxflow(int[][] c, int s, int t) {
    int n = c.length;
    int[][] f = new int[n][n];
    int[] parent = new int[n];
    while ((findAugmentingPath(c, f, s, t, parent) == 1)) {
      augment(f, s, t, parent, bottleneck(c, f, s, t, parent));
    }
    return f;
  }

  public int bottleneck(int[][] c, int[][] f, int s, int v, int[] parent) {
    if ((v == s)) {
      return Integer.MAX_VALUE;
    }
    return Math.min(residual(c, f, parent[v], v), bottleneck(c, f, s, parent[v], parent));
  }

  public void augment(int[][] f, int s, int v, int[] parent, int b) {
    if ((v == s)) {
      return;
    }
    int u = parent[v];
    f[u][v] = (f[u][v] + b);
    f[v][u] = (f[v][u] - b);
    augment(f, s, u, parent, b);
  }

  public int findAugmentingPath(int[][] c, int[][] f, int s, int t, int[] parent) {
    boolean[] seen = new boolean[c.length];
    int[] q = new int[c.length];
    q[0] = s;
    seen[s] = true;
    parent[s] = s;
    return bfs(c, f, t, parent, seen, q, 0, 1);
  }

  public int bfs(int[][] c, int[][] f, int t, int[] parent, boolean[] seen, int[] q, int head, int tail) {
    if ((head >= tail)) {
      return 0;
    }
    int u = q[head];
    if ((u == t)) {
      return 1;
    }
    for (int v = 0; v < c.length; v++) {
      {
        if (((!seen[v]) && (residual(c, f, u, v) > 0))) {
          seen[v] = true;
          parent[v] = u;
          q[tail] = v;
          tail = (tail + 1);
        }
      }
    }
    return bfs(c, f, t, parent, seen, q, (head + 1), tail);
  }

  public int residual(int[][] c, int[][] f, int u, int v) {
    return (c[u][v] - f[u][v]);
  }

  public static void main(String[] args) {
    int[][] c = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    EdmondsKarp prog = new EdmondsKarp();
    int[][] result = prog.maxflow(c, 0, 1);
    System.out.println(Arrays.deepToString(result));
  }
}