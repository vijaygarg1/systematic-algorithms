// LLP-Antichain: maximum antichain by advancing chain indices to dominate-free positions.

import java.util.*;

public class LLPAntichain {
  int n;
  int[][] chains;
  int[] len;
  boolean[][] leq;
  int[] G;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int k = 0; k < n; k++) {
      if (((k != j) && leq[chains[j][G[j]]][chains[k][G[k]]])) { t1 = true; break; }
    }
    return ((G[j] < len[j]) && t1);
  }

  private void advance(int j) {
    G[j] = (G[j] + 1);
  }

  public int[] LLPAntichain(int[][] chains, int[] len, boolean[][] leq) {
    this.chains = chains;
    this.len = len;
    this.leq = leq;
    this.n = len.length;
    this.G = new int[n];
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
    return G;
  }

  public static void main(String[] args) {
    int[][] chains = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] len = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    boolean[][] leq = new boolean[][] {{true, false, false}, {false, true, false}, {false, false, true}};
    LLPAntichain prog = new LLPAntichain();
    int[] result = prog.LLPAntichain(chains, len, leq);
    System.out.println(Arrays.toString(result));
  }
}