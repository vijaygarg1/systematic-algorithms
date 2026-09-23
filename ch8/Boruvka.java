// Classical Boruvka MST: repeatedly attach every component to its
// cheapest outgoing edge until one component remains.

import java.util.*;

public class Boruvka {
  public boolean[] mst(int n, int[] U, int[] V, int[] W) {
    int m = U.length;
    boolean[] inTree = new boolean[m];
    int[] cid = new int[n];
    int treeEdges = 0;
    while (treeEdges < n - 1) {
      components(n, U, V, inTree, cid);

      int[] mwe = new int[n];
      double[] dist = new double[n];
      Arrays.fill(mwe, -1);
      Arrays.fill(dist, Double.POSITIVE_INFINITY);

      for (int e = 0; e < m; e++) {
        int i = U[e];
        int j = V[e];
        if (cid[i] != cid[j]) {
          if (W[e] < dist[cid[i]]) {
            dist[cid[i]] = W[e];
            mwe[cid[i]] = e;
          }
          if (W[e] < dist[cid[j]]) {
            dist[cid[j]] = W[e];
            mwe[cid[j]] = e;
          }
        }
      }

      for (int i = 0; i < n; i++) {
        if (cid[i] == i && mwe[i] != -1 && !inTree[mwe[i]]) {
          inTree[mwe[i]] = true;
          treeEdges++;
        }
      }
    }
    return inTree;
  }

  // Aux: cid[v] := least-numbered vertex reachable from v in (V, inTree).
  private void components(int n, int[] U, int[] V, boolean[] inTree, int[] cid) {
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        bfs(i, i, n, U, V, inTree, visited, cid);
      }
    }
  }

  // Aux: BFS from `start`, labelling every reached vertex with `root`.
  private void bfs(int start, int root, int n, int[] U, int[] V, boolean[] inTree,
                    boolean[] visited, int[] cid) {
    int[] queue = new int[n];
    int head = 0, tail = 0;
    queue[tail++] = start;
    visited[start] = true;
    cid[start] = root;
    while (head < tail) {
      int v = queue[head++];
      for (int e = 0; e < U.length; e++) {
        if (inTree[e]) {
          int u = neighborIf(U[e], V[e], v);
          if (u != -1 && !visited[u]) {
            visited[u] = true;
            cid[u] = root;
            queue[tail++] = u;
          }
        }
      }
    }
  }

  // Aux: if edge (a,b) touches v, return the other endpoint, else -1.
  private int neighborIf(int a, int b, int v) {
    if (a == v) return b;
    if (b == v) return a;
    return -1;
  }

  public static void main(String[] args) {
    // 5-vertex graph matching the book's running example (Fig. mst-graph):
    // edges of weight 4 (a-c), 3 (b-c), 7 (a-d), 2 (d-e), plus a few others.
    int[] U = new int[] {0, 1, 0, 3, 1, 2};
    int[] V = new int[] {2, 2, 3, 4, 3, 4};
    int[] W = new int[] {4, 3, 7, 2, 9, 11};
    Boruvka prog = new Boruvka();
    boolean[] result = prog.mst(5, U, V, W);
    System.out.println(Arrays.toString(result));
  }
}
