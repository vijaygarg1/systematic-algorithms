// MandatoryEdges: composition program forcing the edges in subset M
// into the spanning tree.  Halts "infeasible" (returns null) when a
// mandatory edge closes a cycle with previously committed mandatory
// edges; equivalently, when M itself is not acyclic.  Composed onto
// LLP-Kruskal via predicate conjunction:
// [ LLP-Kruskal(E, w, G) && MandatoryEdges(M, G) ].

import java.util.*;

public class MandatoryEdges {
  int n;
  private static final boolean[] _NO_EARLY_EXIT = new boolean[0];
  int[] u;
  int[] v;
  boolean[] M;
  int[] parent;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(M[j])) return false;
    if (G[j]) return false;
    return true;
  }

  private boolean[] advance() {
    if ((find(u[j], parent) == find(v[j], parent))) {
      return null;
    }
    G[j] = true;
    union(u[j], v[j], parent);
    return _NO_EARLY_EXIT;
  }

  public boolean[] MandatoryEdges(int[] u, int[] v, boolean[] M, int[] parent) {
    this.u = u;
    this.v = v;
    this.M = M;
    this.parent = parent;
    this.n = u.length;
    this.G = new boolean[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; boolean[] _r = advance(); if (_r != _NO_EARLY_EXIT) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public int find(int x, int[] parent) {
    while ((parent[x] != x)) {
      parent[x] = parent[parent[x]];
      x = parent[x];
    }
    return x;
  }

  public void union(int a, int b, int[] parent) {
    int ra = find(a, parent);
    int rb = find(b, parent);
    if ((ra != rb)) {
      parent[ra] = rb;
    }
  }

  public static void main(String[] args) {
    int[] u = new int[] {0, 0, 1, 2};
    int[] v = new int[] {0, 0, 1, 1};
    boolean[] M = new boolean[] {false, false, false, false};
    int[] parent = new int[] {0, 0, 0, 2};
    MandatoryEdges prog = new MandatoryEdges();
    boolean[] result = prog.MandatoryEdges(u, v, M, parent);
    System.out.println(Arrays.toString(result));
  }
}