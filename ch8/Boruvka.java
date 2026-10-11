// Classical Boruvka MST (bxx-mst.tex, algo:Boruvka-seq): repeatedly
// attach every component to its cheapest outgoing edge until one
// component remains. The book's own precondition is a connected graph
// (\KwIn{undirected connected weighted graph}), which this is not relied
// on alone: `progress` guards against a disconnected input (some
// component never finding an outgoing edge) so the loop below cannot
// spin forever waiting for a spanning tree that cannot exist.

import java.util.*;

public class Boruvka {
  public boolean[] mst(int n, int[] U, int[] V, int[] W) {
    int m = U.length;
    boolean[] T = new boolean[m];
    int[] cid = new int[n];
    int treeEdges = 0;
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
      int e = 0;
      while ((e < m)) {
        int i = U[e];
        int j = V[e];
        if ((cid[i] != cid[j])) {
          if ((W[e] < dist[cid[i]])) {
            dist[cid[i]] = W[e];
            mwe[cid[i]] = e;
          }
          if ((W[e] < dist[cid[j]])) {
            dist[cid[j]] = W[e];
            mwe[cid[j]] = e;
          }
        }
        e = (e + 1);
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

  public void components(int n, int[] U, int[] V, boolean[] T, int[] cid) {
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
      visited[i] = false;
    }
    int i = 0;
    while ((i < n)) {
      if ((!visited[i])) {
        bfs(i, i, n, U, V, T, visited, cid);
      }
      i = (i + 1);
    }
  }

  public void bfs(int v, int root, int n, int[] U, int[] V, boolean[] T, boolean[] visited, int[] cid) {
    visited[v] = true;
    cid[v] = root;
    int e = 0;
    while ((e < U.length)) {
      if (T[e]) {
        int u = neighborIf(U[e], V[e], v);
        if (((u != (-1)) && (!visited[u]))) {
          bfs(u, root, n, U, V, T, visited, cid);
        }
      }
      e = (e + 1);
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