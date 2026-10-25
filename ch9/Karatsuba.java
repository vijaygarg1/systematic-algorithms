// Karatsuba multiplication: three half-size products instead of four.
//
// The book writes the middle product as Karatsuba(x0+x1, y0+y1, ceil(n/2)+1),
// where ceil(n/2)+1 is a BOUND on the digit length of the two sums, not a
// recursion driver: at n = 2 it is again 2, so taking it literally never
// reaches the n = 1 base case (confirmed: StackOverflowError). `half` is
// passed instead. Correctness does not depend on the third argument being
// the exact digit count -- the split X = x1*10^h + x0 with x0 = X - x1*10^h
// is valid for any X and h, and the base case multiplies whatever it is
// given -- so the only effect is that the recursion terminates.

import java.util.*;

public class Karatsuba {
  public int Karatsuba(int X, int Y, int n) {
    if ((n == 1)) {
      return (X * Y);
    }
    int half = (n / 2);
    int divisor = pow10(half);
    int x1 = (X / divisor);
    int x0 = (X - (x1 * divisor));
    int y1 = (Y / divisor);
    int y0 = (Y - (y1 * divisor));
    int p1 = Karatsuba(x0, y0, half);
    int p2 = Karatsuba(x1, y1, half);
    int p3 = Karatsuba((x0 + x1), (y0 + y1), half);
    int middle = ((p3 - p1) - p2);
    return (((p2 * pow10(n)) + (middle * divisor)) + p1);
  }

  public int pow10(int k) {
    int r = 1;
    for (int i = 0; i < k; i++) {
      {
        r = (r * 10);
      }
    }
    return r;
  }

  public static void main(String[] args) {
    int X = 0;
    int Y = 0;
    int n = 3;
    Karatsuba prog = new Karatsuba();
    int result = prog.Karatsuba(X, Y, n);
    System.out.println(result);
  }
}