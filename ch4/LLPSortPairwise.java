// LLP-Sort-Pairwise, exactly as bxx-sort.tex's Algorithm LLP-Sort-Pairwise
// states it:
//
// Forbidden (j in [1..n-1]): exists k > j: A[j] > A[k]   // pick such k
// Advance                  : swap(A[j], A[k])
//
// The book writes the witness as "exists k > j" and picks it in a comment;
// LL binds it with `pick k`, and bounds it by the array, so the book's
// k > j is [j+1..n-1] here. The book's [1..n-1] is [0..n-2] 0-based.

import java.util.*;

public class LLPSortPairwise {
  int n;
  int[] A;
  int j;
  int picked_k;

  private boolean forbidden(int j) {
    for (int k = (j + 1); k < n; k++) {
      if ((A[j] > A[k])) { this.picked_k = k; return true; }
    }
    return false;
  }

  private void advance() {
    int k = picked_k;
    { int tmp = A[j]; A[j] = A[k]; A[k] = tmp; }
  }

  public void LLPSortPairwise(int[] A) {
    this.A = A;
    this.n = A.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j <= (n - 2); j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    LLPSortPairwise prog = new LLPSortPairwise();
    prog.LLPSortPairwise(A);
    System.out.println(Arrays.toString(A));
  }
}