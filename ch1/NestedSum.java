// Σ_{i,j < n} i*j computed by a doubly-nested loop.

import java.util.*;

public class NestedSum {
  public int NestedSum(int n) {
    int total = 0;
    for (int i = 0; i < n; i++) {
      {
        for (int j = 0; j < n; j++) {
          {
            total = (total + (i * j));
          }
        }
      }
    }
    return total;
  }

  public static void main(String[] args) {
    int n = 3;
    NestedSum prog = new NestedSum();
    int result = prog.NestedSum(n);
    System.out.println(result);
  }
}