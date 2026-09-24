// LLP-BitonicMerge: forbidden when a pair at distance m is inverted.

import java.util.*;

public class LLPBitonicMerge {
  int n;
  int[] A;
  int i;
  int m;

  private boolean forbidden(int i, int m) {
    if (!((A[i] > A[(i + m)]))) return false;
    return true;
  }

  private void advance() {
    { int tmp = A[i]; A[i] = A[(i + m)]; A[(i + m)] = tmp; }
  }

  public void LLPBitonicMerge(int[] A, int n) {
    this.A = A;
    this.n = n;
    this.n = A.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 0; i < n; i++) {
          for (int m = 0; m < n; m++) {
            if (forbidden(i, m)) {
              this.i = i; this.m = m; advance();
              changed = true;
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPBitonicMerge prog = new LLPBitonicMerge();
    prog.LLPBitonicMerge(A, A.length);
    System.out.println(Arrays.toString(A));
  }
}