// Return the largest element of A.

import java.util.*;

public class MaxElement {
  public int MaxElement(int[] A) {
    int max = A[0];
    for (int i = 1; i < A.length; i++) {
      {
        if ((A[i] > max)) {
          max = A[i];
        }
      }
    }
    return max;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    MaxElement prog = new MaxElement();
    int result = prog.MaxElement(A);
    System.out.println(result);
  }
}