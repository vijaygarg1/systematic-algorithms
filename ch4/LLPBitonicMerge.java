// LLP-BitonicMerge: forbidden when a pair at distance m is inverted.
// Flattened to single index j encoding (i, m) pairs.

import java.util.*;

public class LLPBitonicMerge {
  int n;
  int[] A;
  int[] idx;
  int[] gap;

  private boolean forbidden(int j) {
    if (!((A[idx[j]] > A[(idx[j] + gap[j])]))) return false;
    return true;
  }

  private void advance(int j) {
    { int tmp = A[idx[j]]; A[idx[j]] = A[(idx[j] + gap[j])]; A[(idx[j] + gap[j])] = tmp; }
  }

  public void LLPBitonicMerge(int[] A, int[] idx, int[] gap) {
    this.A = A;
    this.idx = idx;
    this.gap = gap;
    this.n = A.length;
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
  }

  public static void main(String[] args) {
    int[] A = new int[] {1, 4, 7};
    int[] idx = new int[] {2, 3, 5, 8};
    int[] gap = new int[] {6, 9};
    LLPBitonicMerge prog = new LLPBitonicMerge();
    prog.LLPBitonicMerge(A, idx, gap);
    System.out.println(Arrays.toString(A));
    System.out.println(Arrays.toString(idx));
    System.out.println(Arrays.toString(gap));
  }
}