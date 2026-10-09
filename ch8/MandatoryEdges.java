// MandatoryEdges: composition program forcing the edges in subset M
// into the spanning tree.  Halts "infeasible" (returns null) when a
// mandatory edge closes a cycle with previously committed mandatory
// edges; equivalently, when M itself is not acyclic.  Composed onto
// LLP-Kruskal via predicate conjunction:
// [ LLP-Kruskal(E, w, G) && MandatoryEdges(M, G) ].
// Calls the sibling UnionFind program (`UnionFind.find(parent, x)`,
// `UnionFind.union(parent, rank, x, y)`) instead of keeping its own
// copy of find/union -- same multi-file treatment as Kruskal.llp. This
// makes MandatoryEdges.java a multi-file program: it requires
// UnionFind.java alongside it to compile (see JAVA_MULTI_FILE in
// validate-site.py and the `javaRequires` demo-spec field in llp.js).

import java.util.*;

public class MandatoryEdges {
  int n;
  private static final boolean[] _NO_EARLY_EXIT = new boolean[0];
  int[] u;
  int[] v;
  boolean[] M;
  int[] parent;
  boolean[] G;
  int[] rank;
  int j;

  private boolean forbidden(int j) {
    if (!(M[j])) return false;
    if (G[j]) return false;
    return true;
  }

  private boolean[] advance() {
    if ((new UnionFind().find(parent, u[j]) == new UnionFind().find(parent, v[j]))) {
      return null;
    }
    G[j] = true;
    new UnionFind().union(parent, rank, u[j], v[j]);
    return _NO_EARLY_EXIT;
  }

  public boolean[] MandatoryEdges(int[] u, int[] v, boolean[] M, int[] parent) {
    this.u = u;
    this.v = v;
    this.M = M;
    this.parent = parent;
    this.n = u.length;
    this.G = new boolean[n];
    this.rank = new int[parent.length];
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