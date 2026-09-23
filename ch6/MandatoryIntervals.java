// Mandatory: composition program forcing the intervals in subset S
// into the schedule.  Halts ("returns null") when two mandatory
// intervals overlap.  Composed with LLP-IntervalScheduling.

import java.util.*;

public class MandatoryIntervals {
  int n;
  int[] s;
  int[] f;
  boolean[] S;
  boolean[] G;

  private boolean overlap(int j, int k) {
    return s[j] < f[k] && s[k] < f[j];
  }

  private boolean forbidden(int j) {
    return S[j] && !G[j];
  }

  // Returns false if the advance failed (infeasible).
  private boolean advance(int j) {
    for (int k = 0; k < n; k++) {
      if (G[k] && overlap(j, k)) return false;
    }
    G[j] = true;
    return true;
  }

  public boolean[] run(int[] s, int[] f, boolean[] S) {
    this.s = s; this.f = f; this.S = S;
    this.n = s.length;
    this.G = new boolean[n];
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int j = 0; j < n; j++) {
        if (forbidden(j)) {
          if (!advance(j)) return null;
          changed = true;
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = {0, 3, 5};
    int[] f = {2, 7, 9};
    boolean[] S = {true, false, true};
    boolean[] G = new MandatoryIntervals().run(s, f, S);
    System.out.println(G == null ? "infeasible" : Arrays.toString(G));
  }
}
