// Mandatory: composition program forcing the intervals in subset S
// into the schedule.  Halts with "infeasible" (returns null) when two
// mandatory intervals overlap.  Composed onto LLP-IntervalScheduling
// via predicate conjunction:
// [ LLP-IntervalScheduling(s, f, G) && Mandatory(S, G) ].

import java.util.*;

public class MandatoryIntervals {
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
    if (_q1(j)) {
      return null;
    }
    G[j] = true;
    return _NO_EARLY_EXIT;
  }

  public boolean[] MandatoryIntervals(int[] s, int[] f, boolean[] S) {
    this.s = s;
    this.f = f;
    this.S = S;
    this.n = s.length;
    this.n = s.length;
    this.G = new boolean[n];
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

  public boolean overlap(int j, int k, int[] s, int[] f) {
    return ((s[j] < f[k]) && (s[k] < f[j]));
  }

  private boolean _q1(int j) {
    for (int k = 0; k < n; k++) {
      if ((G[k] && overlap(j, k, s, f))) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    boolean[] S = new boolean[] {false, false, false, false};
    MandatoryIntervals prog = new MandatoryIntervals();
    boolean[] result = prog.MandatoryIntervals(s, f, S);
    System.out.println(Arrays.toString(result));
  }
}