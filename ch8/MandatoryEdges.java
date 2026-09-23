// MandatoryEdges: composition program forcing the edges in subset M
// into the spanning tree.  Returns null when a mandatory edge closes
// a cycle with previously committed mandatory edges.

import java.util.*;

public class MandatoryEdges {
  int[] parent;

  int find(int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
  }

  void union(int a, int b) {
    int ra = find(a), rb = find(b);
    if (ra != rb) parent[ra] = rb;
  }

  public boolean[] run(int[] u, int[] v, boolean[] M, int n) {
    int m = u.length;
    this.parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    boolean[] G = new boolean[m];
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int j = 0; j < m; j++) {
        if (M[j] && !G[j]) {
          if (find(u[j]) == find(v[j])) return null;
          G[j] = true; union(u[j], v[j]); changed = true;
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // Triangle 0-1-2 with edges 0:(0,1), 1:(1,2), 2:(0,2).
    int[] u = {0, 1, 0};
    int[] v = {1, 2, 2};
    boolean[] M = {true, true, true};   // all three mandatory => cycle
    boolean[] G = new MandatoryEdges().run(u, v, M, 3);
    System.out.println(G == null ? "infeasible" : Arrays.toString(G));
  }
}
