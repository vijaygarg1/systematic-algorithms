// LLP-Sort-Adjacent, exactly as bxx-sort.tex's Algorithm LLP-Sort-Adjacent
// states it:
//
// Forbidden (j in [1..n-1]): A[j] > A[j+1]
// Advance                  : swap(A[j], A[j+1])   // increment G[j]
//
// G[j] counts the inversions at j; forbidden means G[j] misses one. The
// book's [1..n-1] is every index that has a successor; 0-based that is
// [0..n-2], so the range itself rules out j = n-1 and the predicate needs
// no bound of its own.

import java.util.*;

public class LLPSortAdjacent {
  int n;
  int[] A;
  int j;

  private boolean forbidden(int j) {
    if (!((A[j] > A[(j + 1)]))) return false;
    return true;
  }

  private void advance() {
    { int tmp = A[j]; A[j] = A[(j + 1)]; A[(j + 1)] = tmp; }
  }

  public void LLPSortAdjacent(int[] A) {
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
    LLPSortAdjacent prog = new LLPSortAdjacent();
    prog.LLPSortAdjacent(A);
    System.out.println(Arrays.toString(A));
  }
}