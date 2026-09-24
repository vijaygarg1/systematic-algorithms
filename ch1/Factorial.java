// Compute n! via straight recursion.

import java.util.*;

public class Factorial {
  public int Factorial(int n) {
    if ((n == 0)) {
      return 1;
    }
    return (n * Factorial((n - 1)));
  }

  public static void main(String[] args) {
    int n = 0;
    Factorial prog = new Factorial();
    int result = prog.Factorial(n);
    System.out.println(result);
  }
}