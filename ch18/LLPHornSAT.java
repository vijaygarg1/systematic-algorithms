// LLP Horn SAT: forbidden when an implication's antecedents are all true
// but the consequent x_j is false; advance sets x_j to true.

import java.util.*;

public class LLPHornSAT {
  int n;
  int[][] body;
  int[] head;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(hornImplied(j, G, body, head))) return false;
    return true;
  }

  private void advance() {
    G[j] = true;
  }

  public boolean[] LLPHornSAT(int[][] body, int[] head) {
    this.body = body;
    this.head = head;
    this.n = head.length;
    this.n = head.length;
    this.G = new boolean[n];
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
    int[][] body = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] head = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPHornSAT prog = new LLPHornSAT();
    boolean[] result = prog.LLPHornSAT(body, head);
    System.out.println(Arrays.toString(result));
  }
}