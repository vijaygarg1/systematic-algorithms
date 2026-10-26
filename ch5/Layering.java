// Classical in-degree zero queue algorithm for DAG layering.

import java.util.*;

public class Layering {
  public int[] layer(int[][] pre, int[][] succ) {
    int n = pre.length;
    int[] G = new int[n];
    int[] indeg = new int[n];
    for (int j = 0; j < n; j++) {
      for (int i : pre[j]) {
        indeg[j] = (indeg[j] + 1);
      }
    }
    int[] Q = new int[n];
    int head = 0;
    int tail = 0;
    for (int j = 0; j < n; j++) {
      if ((indeg[j] == 0)) {
        G[j] = 0;
        Q[tail] = j;
        tail = (tail + 1);
      }
    }
    while ((head < tail)) {
      int j = Q[head];
      head = (head + 1);
      for (int k : succ[j]) {
        {
          indeg[k] = (indeg[k] - 1);
          if ((indeg[k] == 0)) {
            int best = 0;
            for (int i : pre[k]) {
              if (((G[i] + 1) > best)) {
                best = (G[i] + 1);
              }
            }
            G[k] = best;
            Q[tail] = k;
            tail = (tail + 1);
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: Layering's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}