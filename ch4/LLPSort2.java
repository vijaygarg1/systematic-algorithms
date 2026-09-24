// LLP-Sort2: forbidden when some k > j has A[j] > A[k]; swap them.

import java.util.*;

public class LLPSort2 {
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

  public void LLPSort2(int[] A) {
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
    LLPSort2 prog = new LLPSort2();
    prog.LLPSort2(A);
    System.out.println(Arrays.toString(A));
  }
}