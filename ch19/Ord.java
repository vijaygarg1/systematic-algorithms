// Ord: multiplicative order of a modulo n via descending LLP on divisor lattice.
// G[i] = exponent of p[i] in the current candidate k = prod p[i]^G[i].

import java.util.*;

public class Ord {
  public int Ord(int a, int n, int[] p, int[] e) {
    int s = p.length;
    int[] G = e.clone();
    int k = 1;
    int i = 0;
    while ((i < s)) {
      int j = 0;
      while ((j < e[i])) {
        k = (k * p[i]);
        j = (j + 1);
      }
      i = (i + 1);
    }
    boolean changed = true;
    while (changed) {
      changed = false;
      i = 0;
      while ((i < s)) {
        if (((G[i] > 0) && (modpow(a, (k / p[i]), n) == 1))) {
          G[i] = (G[i] - 1);
          k = (k / p[i]);
          changed = true;
        }
        i = (i + 1);
      }
    }
    return k;
  }

  public int modpow(int base, int exp, int mod) {
    int result = 1;
    int b = (base % mod);
    int e = exp;
    while ((e > 0)) {
      if (((e % 2) == 1)) {
        result = ((result * b) % mod);
      }
      e = (e / 2);
      b = ((b * b) % mod);
    }
    return result;
  }

  public static void main(String[] args) {
    int a = 0;
    int[] p = new int[] {2, 3, 5, 7};
    int[] e = new int[] {0, 0, 1, 2};
    Ord prog = new Ord();
    int result = prog.Ord(a, p.length, p, e);
    System.out.println(result);
  }
}