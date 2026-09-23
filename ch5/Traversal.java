// Generic queue-based reachability from vertex 0.

import java.util.*;

public class Traversal {
  public int[] reach(int[][] dep) {
    int n = dep.length;
    int[] G = new int[n];
    int[] Q = new int[n];
    int head = 0;
    int tail = 0;
    G[0] = 1;
    Q[tail] = 0;
    tail = (tail + 1);
    while ((head < tail)) {
      int j = Q[head];
      head = (head + 1);
      for (int k : dep[j]) {
        if ((G[k] == 0)) {
          G[k] = 1;
          Q[tail] = k;
          tail = (tail + 1);
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // Demo harness for Traversal.
    // Construct with hard-coded inputs and call the entry method.
  }
}