// Classical Boruvka minimum spanning tree: in each round every current
// component picks its own cheapest outgoing edge and all those edges are
// added at once, so the number of components at least halves per round
// and O(log n) rounds suffice.

import java.util.*;

public class Boruvka {
  public boolean[] mst(int n, int[] U, int[] V, int[] W) {
    int m = U.length;
    int treeEdges = 0;
    boolean[] T = new boolean[m];
    int[] cid = new int[n];
    boolean progress = true;
    while (((treeEdges < (n - 1)) && progress)) {
      int roundStart = treeEdges;
      components(n, U, V, T, cid);
      int[] mwe = new int[n];
      double[] dist = new double[n];
      for (int i = 0; i < n; i++) {
        mwe[i] = (-1);
      }
      for (int i = 0; i < n; i++) {
        dist[i] = Integer.MAX_VALUE;
      }
      for (int e = 0; e < m; e++) {
        if ((cid[U[e]] != cid[V[e]])) {
          relax(cid[U[e]], e, W, dist, mwe);
          relax(cid[V[e]], e, W, dist, mwe);
        }
      }
      for (int i = 0; i < n; i++) {
        if ((((cid[i] == i) && (mwe[i] != (-1))) && (!T[mwe[i]]))) {
          T[mwe[i]] = true;
          treeEdges = (treeEdges + 1);
        }
      }
      progress = (treeEdges > roundStart);
    }
    return T;
  }

  public void relax(int comp, int e, int[] W, double[] dist, int[] mwe) {
    if ((W[e] < dist[comp])) {
      dist[comp] = W[e];
      mwe[comp] = e;
    }
  }

  public void components(int n, int[] U, int[] V, boolean[] T, int[] cid) {
    boolean[] visited = new boolean[n];
    int[] deg = new int[n];
    int[] fill = new int[n];
    int[][] tadj = new int[n][];
    for (int e = 0; e < U.length; e++) {
      {
        if (T[e]) {
          deg[U[e]] = (deg[U[e]] + 1);
          deg[V[e]] = (deg[V[e]] + 1);
        }
      }
    }
    for (int i = 0; i < n; i++) {
      tadj[i] = new int[deg[i]];
    }
    for (int e = 0; e < U.length; e++) {
      {
        if (T[e]) {
          tadj[U[e]][fill[U[e]]] = V[e];
          fill[U[e]] = (fill[U[e]] + 1);
          tadj[V[e]][fill[V[e]]] = U[e];
          fill[V[e]] = (fill[V[e]] + 1);
        }
      }
    }
    for (int i = 0; i < n; i++) {
      {
        if ((!visited[i])) {
          bfs(i, i, visited, cid, tadj);
        }
      }
    }
  }

  public void bfs(int v, int root, boolean[] visited, int[] cid, int[][] tadj) {
    visited[v] = true;
    cid[v] = root;
    for (int q = 0; q < tadj[v].length; q++) {
      {
        int u = tadj[v][q];
        if ((!visited[u])) {
          bfs(u, root, visited, cid, tadj);
        }
      }
    }
  }

  public int neighborIf(int a, int b, int v) {
    if ((a == v)) {
      return b;
    }
    if ((b == v)) {
      return a;
    }
    return (-1);
  }

  public static void main(String[] args) {
    int[] U = new int[] {0, 0, 1, 2};
    int[] V = new int[] {0, 0, 1, 1};
    int[] W = new int[] {0, 0, 0, 2};
    Boruvka prog = new Boruvka();
    boolean[] result = prog.mst(U.length, U, V, W);
    System.out.println(Arrays.toString(result));
  }
}