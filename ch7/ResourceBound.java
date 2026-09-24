// ResourceBound: composition program enforcing a resource budget B on
// the shortest-path relaxation chain.  rho[j] is the total resource
// consumed along the chain that established G[j]; the constraint is
// rho[j] <= B.  Composed onto LLP-Dijkstra via predicate conjunction:
// [ LLP-Dijkstra-Res(s, w, r, G, rho) && ResourceBound(rho, B) ].

import java.util.*;

public class ResourceBound {
  int n;
  private static final int[] _NO_EARLY_EXIT = new int[0];
  int[] rho;
  int B;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((rho[j] > B))) return false;
    return true;
  }

  private int[] advance() {
    return null;
  }

  public int[] ResourceBound(int[] rho, int B, int[] G) {
    this.rho = rho;
    this.B = B;
    this.G = G;
    this.n = rho.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; int[] _r = advance(); if (_r != _NO_EARLY_EXIT) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] rho = new int[] {1, 4, 7};
    int B = 0;
    int[] G = new int[] {2, 3, 5, 8};
    ResourceBound prog = new ResourceBound();
    int[] result = prog.ResourceBound(rho, B, G);
    System.out.println(Arrays.toString(result));
  }
}