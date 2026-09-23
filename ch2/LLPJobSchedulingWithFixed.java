// Kahn's-style topological sweep: finalise each job once its predecessors are fixed.

import java.util.*;

public class LLPJobSchedulingWithFixed {
  public int[] LLPJobSchedulingWithFixed(int[] t, int[][] pre, int[][] succ) {
    int n = t.length;
    int[] G = new int[n];
    int[] count = new int[n];
    int[] candidate = new int[n];
    int head = 0;
    int tail = 0;
    for (int k = 0; k < n; k++) {
      G[k] = t[k];
    }
    for (int k = 0; k < n; k++) {
      count[k] = pre[k].length;
    }
    for (int k = 0; k < n; k++) {
      if ((count[k] == 0)) {
        candidate[tail] = k;
        tail = (tail + 1);
      }
    }
    while ((head < tail)) {
      int j = candidate[head];
      head = (head + 1);
      int m = G[j];
      for (int i : pre[j]) m = Math.max(m, (G[i] + t[j]));
      G[j] = m;
      for (int k : succ[j]) {
        {
          count[k] = (count[k] - 1);
          if ((count[k] == 0)) {
            candidate[tail] = k;
            tail = (tail + 1);
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // Demo harness for LLPJobSchedulingWithFixed.
    // Construct with hard-coded inputs and call the entry method.
  }
}