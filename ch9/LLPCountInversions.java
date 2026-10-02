// LLP-CountInversions: count inversions to the left of each index.

import java.util.*;

public class LLPCountInversions {
  int n;
  int[] A;
  int[] G;

  private boolean forbidden(int j) {
    if (!((G[j] < countLeft(A, j)))) return false;
    return true;
  }

  private void advance(int j) {
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
            advance(j);
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
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPCountInversions prog = new LLPCountInversions();
    prog.LLPCountInversions(A);
    System.out.println(Arrays.toString(A));
  }
}