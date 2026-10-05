// LLP-Kruskal: edge-inclusion lattice driven by union-find.

import java.util.*;

public class LLPKruskal {
  int n;
  int[] u;
  int[] v;
  int[] parent;
  boolean[] C;
  int j;

  private boolean forbidden(int j) {
    if (C[j]) return false;
    if (!((find(u[j], parent) != find(v[j], parent)))) return false;
    return true;
  }

  private void advance() {
    C[j] = true;
    union(u[j], v[j], parent);
  }

  public boolean[] LLPKruskal(int[] u, int[] v, int[] parent) {
    this.u = u;
    this.v = v;
    this.parent = parent;
    this.n = u.length;
    this.C = new boolean[n];
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
    int[] parent = new int[] {0, 0, 0, 2};
    LLPKruskal prog = new LLPKruskal();
    boolean[] result = prog.LLPKruskal(u, v, parent);
    System.out.println(Arrays.toString(result));
  }
}