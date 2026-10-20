// LLP parallel H_n-approximation for Set Cover: pick every set that
// maximises coverage among its neighbours and is lex-minimal among ties.
// A neighbour of j is another unpicked set sharing an uncovered element
// with j; j is forbidden (i.e. should be picked) when it still covers
// something and no neighbour outranks it.
//
// S and G are program fields, so the aux methods read them directly
// rather than threading them through every call, and the tie-break scan
// is the single `exists ... where` it is defined as.

import java.util.*;

public class LLPLexGreedySetCover {
  int n;
  int[][] S;
  int m;
  int u;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(isLexMaxCov(j))) return false;
    return true;
  }

  private void advance() {
    G[j] = true;
  }

  public boolean[] LLPLexGreedySetCover(int[][] S) {
    this.S = S;
    this.n = S.length;
    this.m = S.length;
    this.u = S[0].length;
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

  public boolean isLexMaxCov(int j) {
    boolean t1 = false;
    for (int k = 0; k < m; k++) {
      if (!((((k != j) && (!G[k])) && shareUncovered(j, k)))) continue;
      if (((coverage(k) > coverage(j)) || ((coverage(k) == coverage(j)) && (k < j)))) { t1 = true; break; }
    }
    return (((!G[j]) && (coverage(j) > 0)) && (!t1));
  }

  public int coverage(int j) {
    int t2 = 0;
    for (int e = 0; e < u; e++) {
      if (((S[j][e] == 1) && (!isCovered(e)))) t2++;
    }
    return t2;
  }

  public boolean isCovered(int e) {
    boolean t3 = false;
    for (int s = 0; s < m; s++) {
      if ((G[s] && (S[s][e] == 1))) { t3 = true; break; }
    }
    return t3;
  }

  public boolean shareUncovered(int j, int k) {
    boolean t4 = false;
    for (int e = 0; e < u; e++) {
      if ((((S[j][e] == 1) && (S[k][e] == 1)) && (!isCovered(e)))) { t4 = true; break; }
    }
    return t4;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPLexGreedySetCover prog = new LLPLexGreedySetCover();
    boolean[] result = prog.LLPLexGreedySetCover(S);
    System.out.println(Arrays.toString(result));
  }
}