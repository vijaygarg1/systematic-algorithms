// MandatoryMST: composition program forcing the edges in subset M
// into the spanning tree.  Halts "infeasible" (returns null) when a
// mandatory edge closes a cycle with previously committed mandatory
// edges; equivalently, when M itself is not acyclic.  Composed onto
// LLP-Kruskal via predicate conjunction:

import java.util.*;

public class MandatoryMST {
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

  public boolean[] MandatoryMST(int[] u, int[] v, boolean[] M, int[] parent) {
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
    MandatoryMST prog = new MandatoryMST();
    boolean[] result = prog.MandatoryMST(u, v, M, parent);
    System.out.println(Arrays.toString(result));
  }
}