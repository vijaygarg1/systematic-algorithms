// Mandatory-edge constraint for MST: mandatory edges must be included.
// M[j] is true iff edge j is mandatory.

import java.util.*;

public class MandatoryMST {
  int n;
  boolean[] M;
  int[] u;
  int[] v;
  int[] parent;
  boolean[] G;

  private boolean forbidden(int j) {
    if (!(M[j])) return false;
    if (G[j]) return false;
    return true;
  }

  private void advance(int j) {
    if ((find(u[j], parent) == find(v[j], parent))) {
      return null;
    } else {
      G[j] = true;
      union(u[j], v[j], parent);
    }
  }

  public void MandatoryMST(boolean[] M, int[] u, int[] v, int[] parent, boolean[] G) {
    this.M = M;
    this.u = u;
    this.v = v;
    this.parent = parent;
    this.G = G;
    this.n = M.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            advance(j);
            changed = true;
          }
        }
      }
    }
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
    boolean[] M = new boolean[] {false, false, false, false};
    int[] u = new int[] {1, 4, 7};
    int[] v = new int[] {2, 3, 5, 8};
    int[] parent = new int[] {6, 9};
    boolean[] G = new boolean[] {false, false, false, false};
    MandatoryMST prog = new MandatoryMST();
    prog.MandatoryMST(M, u, v, parent, G);
    System.out.println(Arrays.toString(M));
    System.out.println(Arrays.toString(u));
    System.out.println(Arrays.toString(v));
    System.out.println(Arrays.toString(parent));
    System.out.println(Arrays.toString(G));
  }
}