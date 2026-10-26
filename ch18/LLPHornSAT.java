// LLP Horn SAT (bxx-horn.tex Alg LLP-HornSAT). head[c] = clause c's consequent
// variable, or -1 for a pure negative (goal) clause. forbidden ranges over
// VARIABLES, not clauses; goal clauses are checked once after the least model,
// since violating one cannot be repaired by raising G.

import java.util.*;

public class LLPHornSAT {
  int n;
  int[][] body;
  int[] head;
  boolean[] G;
  int[] hcount;
  int[][] byHead;
  int[] hfill;
  int j;

  private boolean _forbidden0(int j) {
    if (!(definiteImplied(j))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = true;
  }

  public boolean[] LLPHornSAT(int[][] body, int[] head, int n) {
    this.body = body;
    this.head = head;
    this.n = n;
    this.G = new boolean[n];
    this.hcount = new int[n];
    this.byHead = new int[n][];
    this.hfill = new int[n];
    for (int c = 0; c < body.length; c++) {
      if ((head[c] >= 0)) {
        hcount[head[c]] = (hcount[head[c]] + 1);
      }
    }
    for (int v = 0; v < n; v++) {
      byHead[v] = new int[hcount[v]];
    }
    for (int c = 0; c < body.length; c++) {
      if ((head[c] >= 0)) {
        byHead[head[c]][hfill[head[c]]] = c;
        hfill[head[c]] = (hfill[head[c]] + 1);
      }
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
    if (goalFired(body, head)) {
      return null;
    }
    return G;
  }

  public boolean definiteImplied(int j) {
    boolean t1 = false;
    for (int t = 0; t < byHead[j].length; t++) {
      if (allTrue(byHead[j][t])) { t1 = true; break; }
    }
    return ((!G[j]) && t1);
  }

  public boolean goalFired(int[][] body, int[] head) {
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