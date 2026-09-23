// Sequential BFS distance from a source vertex (FIFO queue).

import java.util.*;

public class BFS {
  public int[] traverse(int[][] dep, int s) {
    int n = dep.length;
    int[] G = new int[n];
    for (int i = 0; i < n; i++) {
      G[i] = 2147483647;
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
      for (int k : dep[j]) {
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
    // Demo harness for BFS.
    // Construct with hard-coded inputs and call the entry method.
  }
}