// Return the first index i with A[i] = key, or -1 if absent.

import java.util.*;

public class LinearSearch {
  public int LinearSearch(int[] A, int key) {
    int i = 0;
    while ((i < A.length)) {
      if ((A[i] == key)) {
        return i;
      }
      i = (i + 1);
    }
    return (-1);
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    int key = 0;
    LinearSearch prog = new LinearSearch();
    int result = prog.LinearSearch(A, key);
    System.out.println(result);
  }
}