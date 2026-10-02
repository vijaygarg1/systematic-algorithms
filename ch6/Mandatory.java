// Mandatory-subset constraint: certain intervals must be selected.
// S[j] is true iff interval j is mandatory.

import java.util.*;

public class Mandatory {
  int n;
  boolean[] S;
  boolean[] G;

  private boolean forbidden(int j) {
    if (!(S[j])) return false;
    if (G[j]) return false;
    return true;
  }

  private void advance(int j) {
    if (overlaps(j, G)) {
      return null;
    } else {
      G[j] = true;
    }
  }

  public boolean[] Mandatory(boolean[] S, boolean[] G) {
    this.S = S;
    this.G = G;
    this.n = S.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            advance(j);
            changed = true;
          }
        }
      }
    }
    return null;
  }

  public boolean overlaps(int j, boolean[] G) {
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
    boolean[] S = new boolean[] {false, false, false, false};
    boolean[] G = new boolean[] {false, false, false, false};
    Mandatory prog = new Mandatory();
    boolean[] result = prog.Mandatory(S, G);
    System.out.println(Arrays.toString(result));
  }
}