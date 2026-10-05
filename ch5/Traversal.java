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
    // No runnable example: Traversal's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}