// LLP-LIS: G[j] >= G[i] + 1 for every i in pre(j) (i < j with A[i] < A[j]).

import java.util.*;

public class LLPLongestIncreasingSubseq {
  int n;
  int[] A;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int i : pre[j]) {
      if ((G[j] < (G[i] + 1))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
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
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: LLPLongestIncreasingSubseq's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}