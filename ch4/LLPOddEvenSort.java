// LLP-Sort1 with alternating odd/even-phase scheduling for parallelism.

import java.util.*;

public class LLPOddEvenSort {
  int n;
  int[] A;

  private boolean forbidden(int j) {
    if (!((j < (n - 1)))) return false;
    if (!((A[j] > A[(j + 1)]))) return false;
    return true;
  }

  private void advance(int j) {
    { int tmp = A[j]; A[j] = A[(j + 1)]; A[(j + 1)] = tmp; }
  }

  public void LLPOddEvenSort(int[] A) {
    this.A = A;
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
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPOddEvenSort prog = new LLPOddEvenSort();
    prog.LLPOddEvenSort(A);
    System.out.println(Arrays.toString(A));
  }
}