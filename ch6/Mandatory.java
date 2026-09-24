// Mandatory-subset constraint: certain intervals must be selected.
// S[j] is true iff interval j is mandatory.

import java.util.*;

public class Mandatory {
  int n;
  private static final boolean[] _NO_EARLY_EXIT = new boolean[0];
  int[] s;
  int[] f;
  boolean[] S;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(S[j])) return false;
    if (G[j]) return false;
    return true;
  }

  private boolean[] advance() {
    if (overlaps(j, G, s, f)) {
      return null;
    } else {
      G[j] = true;
    }
    return _NO_EARLY_EXIT;
  }

  public boolean[] Mandatory(int[] s, int[] f, boolean[] S, boolean[] G) {
    this.s = s;
    this.f = f;
    this.S = S;
    this.G = G;
    this.n = s.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; boolean[] _r = advance(); if (_r != _NO_EARLY_EXIT) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean overlaps(int j, boolean[] G, int[] s, int[] f) {
    boolean result = false;
    int k = 0;
    while ((k < G.length)) {
      if ((((G[k] && (k != j)) && (s[k] < f[j])) && (s[j] < f[k]))) {
        result = true;
      }
      k = (k + 1);
    }
    return result;
  }

  public static void main(String[] args) {
    int[] s = new int[] {1, 4, 7};
    int[] f = new int[] {2, 3, 5, 8};
    boolean[] S = new boolean[] {false, false, false, false};
    boolean[] G = new boolean[] {false, false, false, false};
    Mandatory prog = new Mandatory();
    boolean[] result = prog.Mandatory(s, f, S, G);
    System.out.println(Arrays.toString(result));
  }
}