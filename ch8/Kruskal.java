// Classical Kruskal MST: sort edges, union-find with rank and path-compression.

import java.util.*;

public class Kruskal {
  public boolean[] mst(int n, int[] U, int[] V, int[] W) {
    int m = U.length;
    boolean[] inTree = new boolean[m];
    int[] parent = new int[n];
    int[] rank = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
    }
    int chosen = 0;
    int e = 0;
    while (((e < m) && (chosen < (n - 1)))) {
      int u = U[e];
      int v = V[e];
      int ru = root(parent, u);
      int rv = root(parent, v);
      if ((ru != rv)) {
        inTree[e] = true;
        chosen = (chosen + 1);
        if ((rank[ru] < rank[rv])) {
          parent[ru] = rv;
        } else {
          if ((rank[ru] > rank[rv])) {
            parent[rv] = ru;
          } else {
            parent[rv] = ru;
            rank[ru] = (rank[ru] + 1);
          }
        }
      }
      e = (e + 1);
    }
    return inTree;
  }

  public int root(int[] parent, int x) {
    if ((parent[x] != x)) {
      parent[x] = root(parent, parent[x]);
    }
    return parent[x];
  }

  public static void main(String[] args) {
    int[] U = new int[] {1, 4, 7};
    int[] V = new int[] {2, 3, 5, 8};
    int[] W = new int[] {6, 9};
    Kruskal prog = new Kruskal();
    boolean[] result = prog.mst(U.length, U, V, W);
    System.out.println(Arrays.toString(result));
  }
}