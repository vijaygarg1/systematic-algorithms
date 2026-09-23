// LLP-QHornSAT: solve q-horn formula by composing HornSAT on X
// variables with 2-SAT on Y variables.
// This is a composite algorithm, not a single forbidden/advance pair.

import java.util.*;

public class LLPQHornSAT {
  int n;
  int[][] body1;
  int[] head1;
  int[][] clauses2;
  int n1;
  int n2;
  boolean[] G;
  boolean[] H;
  int j;
  int j;

  private boolean _forbidden0(int j) {
    if (!(hornImplied(j, G, body1, head1))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = true;
  }

  public boolean[] LLPQHornSAT(int[][] body1, int[] head1, int[][] clauses2, int n1, int n2) {
    this.body1 = body1;
    this.head1 = head1;
    this.clauses2 = clauses2;
    this.n1 = n1;
    this.n2 = n2;
    this.n = head1.length;
    this.G = new boolean[n];
    for (int i = 0; i < n; i++) this.G[i] = new boolean[n1];
    this.H = new boolean[n];
    for (int i = 0; i < n; i++) this.H[i] = new boolean[n2];
    this.j = 0;
    while ((j < n1)) {
      G[j] = false;
      j = (j + 1);
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
    j = 0;
    while ((j < n2)) {
      H[j] = false;
      j = (j + 1);
    }
    return G;
  }

  public boolean hornImplied(int j, boolean[] G, int[][] body, int[] head) {
    if (G[j]) {
      return false;
    }
    int c = 0;
    while ((c < body.length)) {
      if ((head[c] == j)) {
        boolean allTrue = true;
        int k = 0;
        while ((k < body[c].length)) {
          if ((!G[body[c][k]])) {
            allTrue = false;
          }
          k = (k + 1);
        }
        if (allTrue) {
          return true;
        }
      }
      c = (c + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] body1 = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] head1 = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    int[][] clauses2 = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n1 = 0;
    int n2 = 0;
    LLPQHornSAT prog = new LLPQHornSAT();
    boolean[] result = prog.LLPQHornSAT(body1, head1, clauses2, n1, n2);
    System.out.println(Arrays.toString(result));
  }
}