// Recursive DFS recording discovery and finish times.

import java.util.*;

public class DFS {
  public int[] traverse(int[][] dep) {
    int n = dep.length;
    int[] G = new int[n];
    int[] parent = new int[n];
    int[] discovered = new int[n];
    int[] finished = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = (-1);
    }
    int[] t = new int[1];
    t[0] = 1;
    visit(0, dep, G, parent, discovered, finished, t);
    return discovered;
  }

  public void visit(int j, int[][] dep, int[] G, int[] parent, int[] discovered, int[] finished, int[] t) {
    G[j] = 1;
    discovered[j] = t[0];
    t[0] = (t[0] + 1);
    for (int k : dep[j]) {
      if ((G[k] == 0)) {
        parent[k] = j;
        visit(k, dep, G, parent, discovered, finished, t);
      }
    }
    finished[j] = t[0];
    t[0] = (t[0] + 1);
  }

  public static void main(String[] args) {
    // Demo harness for DFS.
    // Construct with hard-coded inputs and call the entry method.
  }
}