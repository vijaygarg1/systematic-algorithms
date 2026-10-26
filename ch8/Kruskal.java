// Classical Kruskal MST: sort edges, union-find with rank and path-compression.
// Calls the sibling UnionFind program (`UnionFind.union(parent, rank, u, v)`,
// compiling to `new UnionFind().union(parent, rank, u, v)`) instead of
// keeping its own copy of find/union-by-rank -- UnionFind.union() already
// finds both roots internally and reports whether they were distinct, so

import java.util.*;

public class Kruskal {
  public boolean[] mst(int n, int[] U, int[] V, int[] W) {
    int m = U.length;
    boolean[] T = new boolean[m];
    int[] parent = new int[n];
    int[] rank = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
    }
    int chosen = 0;
    int e = 0;
    while (((e < m) && (chosen < (n - 1)))) {
      if (new UnionFind().union(parent, rank, U[e], V[e])) {
        T[e] = true;
        chosen = (chosen + 1);
      }
      e = (e + 1);
    }
    return T;
  }

  public static void main(String[] args) {
    int[] U = new int[] {0, 0, 1, 2};
    int[] V = new int[] {0, 0, 1, 1};
    int[] W = new int[] {0, 0, 0, 2};
    Kruskal prog = new Kruskal();
    boolean[] result = prog.mst(U.length, U, V, W);
    System.out.println(Arrays.toString(result));
  }
}