// Sequential BFS distance from a source vertex (FIFO queue).

import java.util.*;

public class BFSTraversal {
  public int[] traverse(int[][] succ, int s) {
    int n = succ.length;
    int[] G = new int[n];
    for (int i = 0; i < n; i++) {
      G[i] = Integer.MAX_VALUE;
    }
    G[s] = 0;
    int[] Q = new int[n];
    int head = 0;
    int tail = 0;
    Q[tail] = s;
    tail = (tail + 1);
    while ((head < tail)) {
      int j = Q[head];
      head = (head + 1);
      for (int k : succ[j]) {
        if ((G[k] > (G[j] + 1))) {
          G[k] = (G[j] + 1);
          Q[tail] = k;
          tail = (tail + 1);
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: BFSTraversal's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}