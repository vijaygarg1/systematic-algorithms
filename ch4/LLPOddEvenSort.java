// LLP-Sort1 with alternating odd/even-phase scheduling for parallelism.

import java.util.*;

public class LLPOddEvenSort {
  int n;
  int[] A;
  int j;

  private boolean forbidden(int j) {
    if (!((j < (n - 1)))) return false;
    if (!((A[j] > A[(j + 1)]))) return false;
    return true;
  }

  private void advance() {
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
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    LLPOddEvenSort prog = new LLPOddEvenSort();
    prog.LLPOddEvenSort(A);
    System.out.println(Arrays.toString(A));
  }
}