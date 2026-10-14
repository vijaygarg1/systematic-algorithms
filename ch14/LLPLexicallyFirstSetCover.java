// LLP parallel H_n-approximation for Set Cover: pick every set that maximises coverage among neighbours and is lex-minimal among ties.

import java.util.*;

public class LLPLexicallyFirstSetCover {
  int n;
  int[][] S;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(isLexMaxCov(j, S, G))) return false;
    return true;
  }

  private void advance() {
    G[j] = true;
  }

  public boolean[] LLPLexicallyFirstSetCover(int[][] S) {
    this.S = S;
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

  public boolean isLexMaxCov(int j, int[][] S, boolean[] G) {
    if (G[j]) {
      return false;
    }
    int m = G.length;
    int cov_j = coverage(j, S, G);
    if ((cov_j == 0)) {
      return false;
    }
    int k = 0;
    while ((k < m)) {
      if ((((k != j) && (!G[k])) && shareUncovered(j, k, S, G))) {
        int cov_k = coverage(k, S, G);
        if ((cov_k > cov_j)) {
          return false;
        }
        if (((cov_k == cov_j) && (k < j))) {
          return false;
        }
      }
      k = (k + 1);
    }
    return true;
  }

  public int coverage(int j, int[][] S, boolean[] G) {
    int t1 = 0;
    for (int e = 0; e < S[j].length; e++) {
      if (((S[j][e] == 1) && (!isCovered(e, S, G)))) t1++;
    }
    return t1;
  }

  public boolean isCovered(int e, int[][] S, boolean[] G) {
    boolean t2 = false;
    for (int s = 0; s < G.length; s++) {
      if ((G[s] && (S[s][e] == 1))) { t2 = true; break; }
    }
    return t2;
  }

  public boolean shareUncovered(int j, int k, int[][] S, boolean[] G) {
    boolean t3 = false;
    for (int e = 0; e < S[j].length; e++) {
      if ((((S[j][e] == 1) && (S[k][e] == 1)) && (!isCovered(e, S, G)))) { t3 = true; break; }
    }
    return t3;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPLexicallyFirstSetCover prog = new LLPLexicallyFirstSetCover();
    boolean[] result = prog.LLPLexicallyFirstSetCover(S);
    System.out.println(Arrays.toString(result));
  }
}