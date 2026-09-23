// Euclid's GCD using the mod operation.

import java.util.*;

public class GCD {
  public static int GCD(int a, int b) {
    while ((a != b)) {
      if ((a > b)) {
        if (((a % b) == 0)) {
          a = b;
        } else {
          a = (a % b);
        }
      } else {
        if (((b % a) == 0)) {
          b = a;
        } else {
          b = (b % a);
        }
      }
    }
    return a;
  }

  public static void main(String[] args) {
    int a = 0;
    int b = 0;
    int result = GCD(a, b);
    System.out.println(result);
  }
}