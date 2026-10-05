// Extended Euclidean algorithm, cast as an LLP forbidden/advance program
// (matching the book's LLP-ExtGCD algorithm box in bxx-numberTheory.tex,
// \S Extended GCD Algorithm): maintains G alongside Bezout coefficients
// (X, Y) with the invariant G[k] = X[k]*a + Y[k]*b. Forbidden picks the
// larger of the two entries; advance reduces it via the same quotient
// q := floor((G[j]-1)/G[i]) used by LLP-GCD, applying the identical
// subtraction to the Bezout pair so the invariant is preserved. At
// termination G[0] = G[1] = gcd(a,b) and (X[0],Y[0]) is a Bezout pair.
//
// All three state arrays (G, X, Y) must be declared consecutively, before
// any assignment statement -- the parser only promotes a *leading,
// uninterrupted* run of `TYPE NAME = ...;` declarations to instance
// fields visible from forbidden/advance; an assignment statement between
// two declarations ends that run early, silently leaving the later ones
// as method-local variables invisible to advance() (a real llc.py
// limitation, confirmed and documented rather than worked around in the
// compiler, since only this one program has needed more than one top-
// level state array so far). `n` likewise has to be set explicitly here:
// it is normally inferred from an array *parameter*'s length, but G/X/Y
// are locally allocated, not parameters, so nothing would ever set it
// otherwise -- left at Java's default 0, the main loop would silently
// iterate zero times.

import java.util.*;

public class ExtGCD {
  int n;
  int a;
  int b;
  int[] G;
  int[] X;
  int[] Y;
  int j;
  int picked_i;

  private boolean _forbidden0(int j) {
    for (int i = 0; i <= 1; i++) {
      if ((G[j] > G[i])) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void _advance0() {
    int i = picked_i;
    int q = ((G[j] - 1) / G[i]);
    G[j] = (G[j] - (q * G[i]));
    X[j] = (X[j] - (q * X[i]));
    Y[j] = (Y[j] - (q * Y[i]));
  }

  public int[] ExtGCD(int a, int b) {
    this.a = a;
    this.b = b;
    this.G = new int[2];
    this.X = new int[2];
    this.Y = new int[2];
    n = 2;
    G[0] = a;
    G[1] = b;
    X[0] = 1;
    Y[0] = 0;
    X[1] = 0;
    Y[1] = 1;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
    int[] result = new int[3];
    result[0] = G[0];
    result[1] = X[0];
    result[2] = Y[0];
    return result;
  }

  public static void main(String[] args) {
    int a = 0;
    int b = 0;
    ExtGCD prog = new ExtGCD();
    int[] result = prog.ExtGCD(a, b);
    System.out.println(Arrays.toString(result));
  }
}