// LLP-Kruskal: edge-inclusion lattice driven by union-find.
// Calls the sibling UnionFind program (`UnionFind.find(parent, x)`,
// `UnionFind.union(parent, rank, x, y)`) instead of keeping its own
// copy of find/union -- same multi-file treatment as Kruskal.llp. This
// makes LLPKruskal.java a multi-file program: it requires UnionFind.java
// alongside it to compile (see JAVA_MULTI_FILE in validate-site.py and
// the `javaRequires` demo-spec field in llp.js).

import java.util.*;

public class LLPKruskal {
  int n;
  int[] u;
  int[] v;
  int[] parent;
  boolean[] C;
  int[] rank;
  int j;

  private boolean forbidden(int j) {
    if (C[j]) return false;
    if (!((new UnionFind().find(parent, u[j]) != new UnionFind().find(parent, v[j])))) return false;
    return true;
  }

  private void advance() {
    C[j] = true;
    new UnionFind().union(parent, rank, u[j], v[j]);
  }

  public boolean[] LLPKruskal(int[] u, int[] v, int[] parent) {
    this.u = u;
    this.v = v;
    this.parent = parent;
    this.n = u.length;
    this.C = new boolean[n];
    this.rank = new int[parent.length];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[] u = new int[] {0, 0, 1, 2};
    int[] v = new int[] {0, 0, 1, 1};
    int[] parent = new int[] {0, 0, 0, 2};
    LLPKruskal prog = new LLPKruskal();
    boolean[] result = prog.LLPKruskal(u, v, parent);
    System.out.println(Arrays.toString(result));
  }
}