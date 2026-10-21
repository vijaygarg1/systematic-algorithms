// LLP Horn SAT, matching bxx-horn.tex Algorithm LLP-HornSAT. head[c] is
// the consequent variable of clause c, or -1 if clause c is a pure
// negative (goal) clause. The book's two rules are written here as two
// forbidden/advance clauses joined by &&, so they share one fixpoint.

import java.util.*;

public class LLPHornSAT {
  int n;
  private static final boolean[] _NO_EARLY_EXIT = new boolean[0];
  int[][] body;
  int[] head;
  boolean[] G;
  int j;

  private boolean _forbidden0(int j) {
    if (!(definiteImplied(j))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = true;
  }

  private boolean _forbidden1(int j) {
    if (!(goalFired())) return false;
    return true;
  }

  private boolean[] _advance1() {
    return null;
  }

  public boolean[] LLPHornSAT(int[][] body, int[] head, int n) {
    this.body = body;
    this.head = head;
    this.n = n;
    this.G = new boolean[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
          if (_forbidden1(j)) {
            this.j = j; boolean[] _r = _advance1(); if (_r != _NO_EARLY_EXIT) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean definiteImplied(int j) {
    boolean t1 = false;
    for (int c = 0; c < body.length; c++) {
      if (((head[c] == j) && allTrue(c))) { t1 = true; break; }
    }
    return ((!G[j]) && t1);
  }

  public boolean goalFired() {
    boolean t2 = false;
    for (int c = 0; c < body.length; c++) {
      if (((head[c] == (0 - 1)) && allTrue(c))) { t2 = true; break; }
    }
    return t2;
  }

  public boolean allTrue(int c) {
    boolean t3 = true;
    for (int k = 0; k < body[c].length; k++) {
      if (!(G[body[c][k]])) { t3 = false; break; }
    }
    return t3;
  }

  public static void main(String[] args) {
    int[][] body = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[] head = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    LLPHornSAT prog = new LLPHornSAT();
    boolean[] result = prog.LLPHornSAT(body, head, head.length);
    System.out.println(Arrays.toString(result));
  }
}