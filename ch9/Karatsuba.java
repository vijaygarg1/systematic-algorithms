// Karatsuba multiplication: three half-size products instead of four.

import java.util.*;

public class Karatsuba {
  public int multiply(int X, int Y, int n) {
    if ((n == 1)) {
      return (X * Y);
    }
    int half = (n / 2);
    int divisor = pow10(half);
    int x1 = (X / divisor);
    int x0 = (X - (x1 * divisor));
    int y1 = (Y / divisor);
    int y0 = (Y - (y1 * divisor));
    int p1 = multiply(x0, y0, half);
    int p2 = multiply(x1, y1, half);
    int p3 = multiply((x0 + x1), (y0 + y1), half);
    int middle = ((p3 - p1) - p2);
    return (((p2 * pow10(n)) + (middle * divisor)) + p1);
  }

  public int pow10(int k) {
    int r = 1;
    int i = 0;
    while ((i < k)) {
      r = (r * 10);
      i = (i + 1);
    }
    return r;
  }

  public static void main(String[] args) {
    int X = 0;
    int Y = 0;
    int n = 3;
    Karatsuba prog = new Karatsuba();
    int result = prog.multiply(X, Y, n);
    System.out.println(result);
  }
}