// Σ_{i,j < n} i*j computed by a doubly-nested loop.

import java.util.*;

public class NestedSum {
  public static int NestedSum(int n) {
    int total = 0;
    int i = 0;
    while ((i < n)) {
      int j = 0;
      while ((j < n)) {
        total = (total + (i * j));
        j = (j + 1);
      }
      i = (i + 1);
    }
    return total;
  }

  public static void main(String[] args) {
    int n = 0;
    int result = NestedSum(n);
    System.out.println(result);
  }
}