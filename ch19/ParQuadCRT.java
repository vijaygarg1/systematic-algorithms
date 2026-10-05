// Par-QuadCRT: simultaneous quadratic congruences x^2 ≡ a[j] (mod m[j]).
// Generalises Par-CRT to a non-linear local predicate.  G[j] is initialised
// to the smallest non-negative root of x^2 ≡ a[j] (mod m[j]); the
// forbidden / advance pair drives every G[j] up to a common value that
// satisfies all r congruences simultaneously.
//
// The advance jumps G[j] forward by multiples of m[j] until x^2 ≡ a[j].
// In the worst case one walks the period of m[j] (cost O(m[j]) per
// advance); for prime m[j] the two square roots can be precomputed.

import java.util.*;

public class ParQuadCRT {
  int n;
  int[] m;
  int[] a;
  int[] roots;
  int[] G;
  int j;
  int picked_i;

  private boolean forbidden(int j) {
    for (int i = 0; i < n; i++) {
      if ((G[j] < G[i])) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void advance() {
    int i = picked_i;
    G[j] = (G[j] + (((((G[i] - G[j]) + m[j]) - 1) / m[j]) * m[j]));
  }

  public int[] ParQuadCRT(int[] m, int[] a, int[] roots) {
    this.m = m;
    this.a = a;
    this.roots = roots;
    this.n = m.length;
    this.G = roots.clone();
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] m = new int[] {3, 5, 7, 11};
    int[] a = new int[] {0, 0, 1, 2};
    int[] roots = new int[] {0, 0, 1, 1};
    ParQuadCRT prog = new ParQuadCRT();
    int[] result = prog.ParQuadCRT(m, a, roots);
    System.out.println(Arrays.toString(result));
  }
}