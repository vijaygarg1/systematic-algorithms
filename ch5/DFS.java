// Recursive DFS recording discovery and finish times.

import java.util.*;

public class DFS {
  public int[] traverse(int[][] succ) {
    int n = succ.length;
    int[] G = new int[n];
    int[] parent = new int[n];
    int[] discovered = new int[n];
    int[] finished = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = (-1);
    }
    int[] t = new int[1];
    t[0] = 1;
    visit(0, succ, G, parent, discovered, finished, t);
    return discovered;
  }

  public void visit(int j, int[][] succ, int[] G, int[] parent, int[] discovered, int[] finished, int[] t) {
    G[j] = 1;
    discovered[j] = t[0];
    t[0] = (t[0] + 1);
    for (int k : succ[j]) {
      if ((G[k] == 0)) {
        parent[k] = j;
        visit(k, succ, G, parent, discovered, finished, t);
      }
    }
    finished[j] = t[0];
    t[0] = (t[0] + 1);
  }

  public static void main(String[] args) {
    // No runnable example: DFS's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}