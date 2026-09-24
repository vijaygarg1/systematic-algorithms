// Classical Boruvka MST: repeatedly attach every component to its
// cheapest outgoing edge until one component remains.

import java.util.*;

public class Boruvka {
  public boolean[] mst(int n, int[] U, int[] V, int[] W) {
    int m = U.length;
    boolean[] inTree = new boolean[m];
    int[] cid = new int[n];
    int treeEdges = 0;
    while ((treeEdges < (n - 1))) {
      components(n, U, V, inTree, cid);
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
        if ((((cid[i] == i) && (mwe[i] != (-1))) && (!inTree[mwe[i]]))) {
          inTree[mwe[i]] = true;
          treeEdges = (treeEdges + 1);
        }
      }
    }
    return inTree;
  }

  public void components(int n, int[] U, int[] V, boolean[] inTree, int[] cid) {
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
      visited[i] = false;
    }
    int i = 0;
    while ((i < n)) {
      if ((!visited[i])) {
        bfs(i, i, n, U, V, inTree, visited, cid);
      }
      i = (i + 1);
    }
  }

  public void bfs(int start, int root, int n, int[] U, int[] V, boolean[] inTree, boolean[] visited, int[] cid) {
    int[] queue = new int[n];
    int head = 0;
    int tail = 0;
    queue[tail] = start;
    tail = (tail + 1);
    visited[start] = true;
    cid[start] = root;
    while ((head < tail)) {
      int v = queue[head];
      head = (head + 1);
      int e = 0;
      while ((e < U.length)) {
        if (inTree[e]) {
          int u = neighborIf(U[e], V[e], v);
          if (((u != (-1)) && (!visited[u]))) {
            visited[u] = true;
            cid[u] = root;
            queue[tail] = u;
            tail = (tail + 1);
          }
        }
        e = (e + 1);
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
    int[] U = new int[] {1, 4, 7};
    int[] V = new int[] {2, 3, 5, 8};
    int[] W = new int[] {6, 9};
    Boruvka prog = new Boruvka();
    boolean[] result = prog.mst(U.length, U, V, W);
    System.out.println(Arrays.toString(result));
  }
}