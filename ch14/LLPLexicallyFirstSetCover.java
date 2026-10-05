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
    int u = S[j].length;
    int count = 0;
    int e = 0;
    while ((e < u)) {
      if (((S[j][e] == 1) && (!isCovered(e, S, G)))) {
        count = (count + 1);
      }
      e = (e + 1);
    }
    return count;
  }

  public boolean isCovered(int e, int[][] S, boolean[] G) {
    int m = G.length;
    int s = 0;
    while ((s < m)) {
      if ((G[s] && (S[s][e] == 1))) {
        return true;
      }
      s = (s + 1);
    }
    return false;
  }

  public boolean shareUncovered(int j, int k, int[][] S, boolean[] G) {
    int u = S[j].length;
    int e = 0;
    while ((e < u)) {
      if ((((S[j][e] == 1) && (S[k][e] == 1)) && (!isCovered(e, S, G)))) {
        return true;
      }
      e = (e + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPLexicallyFirstSetCover prog = new LLPLexicallyFirstSetCover();
    boolean[] result = prog.LLPLexicallyFirstSetCover(S);
    System.out.println(Arrays.toString(result));
  }
}