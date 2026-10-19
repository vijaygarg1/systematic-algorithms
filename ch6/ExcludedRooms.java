// ExcludedRooms: a constraint program enforcing that course j is not
// assigned a room from its excluded set R[j].  Its predicate is just
// B_excl == forall j: G[j] not in R[j],
// and the forbidden clause below is exactly the negation of that, so
// the advance raises G[j] by the least amount that removes ITS own
// violation.  It does not also avoid the rooms of overlapping courses:
// that is LLP-IntervalPartition's constraint, and the two are composed
// with `&&`, which forbids an index when either rule forbids it and
// takes the advance from the rule that fired.  If this rule moves j
// onto an occupied room, the partition rule fires next.

import java.util.*;

public class ExcludedRooms {
  int n;
  int[][] R;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    boolean t1 = false;
    for (int r : R[j]) {
      if ((G[j] == r)) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    G[j] = leastAllowedRoom(j);
  }

  public int[] ExcludedRooms(int[][] R, int[] G) {
    this.R = R;
    this.G = G;
    this.n = R.length;
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

  public int leastAllowedRoom(int j) {
    int r = G[j];
    boolean excluded = true;
    while (excluded) {
      excluded = false;
      for (int x : R[j]) {
        if ((x == r)) {
          excluded = true;
        }
      }
      if (excluded) {
        r = (r + 1);
      }
    }
    return r;
  }

  public static void main(String[] args) {
    // No runnable example: ExcludedRooms's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}