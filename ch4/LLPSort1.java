// LLP-Sort1 (transposition form): forbidden = inversion, advance = swap.

import java.util.*;

public class LLPSort1 {
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

  public void LLPSort1(int[] A) {
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
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPSort1 prog = new LLPSort1();
    prog.LLPSort1(A);
    System.out.println(Arrays.toString(A));
  }
}