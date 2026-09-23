// ExcludedRooms: composition program enforcing R[j] = excluded rooms.
// G[j] is the room of course j; advance picks the smallest non-excluded
// room not used by any overlapping earlier course.

import java.util.*;

public class ExcludedRooms {
  int n;
  Set<Integer>[] R;
  Set<Integer>[] pre;
  int[] G;

  private boolean forbidden(int j) { return R[j].contains(G[j]); }

  private void advance(int j) {
    for (int r = 1; r <= n; r++) {
      if (R[j].contains(r)) continue;
      boolean clash = false;
      for (int k : pre[j]) if (G[k] == r) { clash = true; break; }
      if (!clash) { G[j] = r; return; }
    }
    // No valid room found within [1..n]; in the composed program this
    // is reached only when the base partition would have used such a
    // value; we return without changing G to allow the next pass.
  }

  public int[] run(Set<Integer>[] R, Set<Integer>[] pre, int[] G) {
    this.R = R; this.pre = pre; this.G = G; this.n = G.length;
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int j = 0; j < n; j++) {
        if (forbidden(j)) {
          int before = G[j];
          advance(j);
          if (G[j] != before) changed = true;
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int n = 3;
    Set<Integer>[] R = new Set[]{ Set.of(1), Set.of(2), Set.of() };
    Set<Integer>[] pre = new Set[]{ Set.of(), Set.of(0), Set.of(0, 1) };
    int[] G = new int[] {1, 2, 1};  // initial, may be overridden by base program
    System.out.println(Arrays.toString(new ExcludedRooms().run(R, pre, G)));
  }
}
