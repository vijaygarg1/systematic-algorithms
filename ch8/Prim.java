// Classical Prim MST: O(n²) linear-scan version using a weight matrix.

import java.util.*;

public class Prim {
  public int[] mst(int[][] w) {
    int n = w.length;
    int[] d = new int[n];
    int[] parent = new int[n];
    boolean[] fixed = new boolean[n];
    for (int i = 0; i < n; i++) {
      {
        d[i] = 2147483647;
        parent[i] = (-1);
      }
    }
    d[0] = 0;
    int count = 0;
    while ((count < n)) {
      int v = (-1);
      int best = 2147483647;
      int k = 0;
      while ((k < n)) {
        if (((!fixed[k]) && (d[k] < best))) {
          v = k;
          best = d[k];
        }
        k = (k + 1);
      }
      if ((v == (-1))) {
        return parent;
      }
      fixed[v] = true;
      count = (count + 1);
      k = 0;
      while ((k < n)) {
        if ((((!fixed[k]) && (w[v][k] != 2147483647)) && (w[v][k] < d[k]))) {
          d[k] = w[v][k];
          parent[k] = v;
        }
        k = (k + 1);
      }
    }
    return parent;
  }

  public static void main(String[] args) {
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    Prim prog = new Prim();
    int[] result = prog.mst(w);
    System.out.println(Arrays.toString(result));
  }
}