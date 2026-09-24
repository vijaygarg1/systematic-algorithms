// Return the largest element of A.

import java.util.*;

public class MaxElement {
  public int MaxElement(int[] A) {
    int max = A[0];
    int i = 1;
    while ((i < A.length)) {
      if ((A[i] > max)) {
        max = A[i];
      }
      i = (i + 1);
    }
    return max;
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    MaxElement prog = new MaxElement();
    int result = prog.MaxElement(A);
    System.out.println(result);
  }
}