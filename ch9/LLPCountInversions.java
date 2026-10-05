// LLP-CountInversions: count inversions to the left of each index.

import java.util.*;

public class LLPCountInversions {
  int n;
  int[] A;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((G[j] < countLeft(A, j)))) return false;
    return true;
  }

  private void advance() {
    G[j] = countLeft(A, j);
  }

  public void LLPCountInversions(int[] A) {
    this.A = A;
    this.n = A.length;
    this.G = new int[n];
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

  public int countLeft(int[] A, int j) {
    int count = 0;
    int i = 0;
    while ((i < j)) {
      if ((A[i] > A[j])) {
        count = (count + 1);
      }
      i = (i + 1);
    }
    return count;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    LLPCountInversions prog = new LLPCountInversions();
    prog.LLPCountInversions(A);
    System.out.println(Arrays.toString(A));
  }
}