// LLP-LIS: G[j] >= G[i] + 1 for every i in pre(j) (i < j with A[i] < A[j]).

import java.util.*;

public class LLPLongestIncreasingSubseq {
  int n;
  int[] A;
  int[][] pre;
  int[] G;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] < (G[i] + 1))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance(int j) {
    int m = Integer.MIN_VALUE;
    for (int i : pre[j]) m = Math.max(m, (G[i] + 1));
    G[j] = m;
  }

  public int[] LLPLongestIncreasingSubseq(int[] A, int[][] pre) {
    this.A = A;
    this.pre = pre;
    this.n = A.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = 1;
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
    // Demo harness for LLPLongestIncreasingSubseq.
    // Construct with hard-coded inputs and call the entry method.
  }
}