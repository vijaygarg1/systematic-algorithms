// LLP-IntervalPartition (bxx-greedy.tex, fig:intervalPartition).  The
// plain feasibility predicate ("overlapping courses get different
// rooms") is not lattice-linear -- [1,2] and [2,1] both satisfy it but
// their meet [1,1] does not -- so the book redefines B as the
// lexicographically least assignment,
// forall j: G[j] = min { r | forall i in pre(j): G[i] != r },
// and this program's forbidden predicate is exactly the negation of
// that, localized at j.  Processing courses in index order --
// priority(j) = j, which is what the boolean `fixed` array implements
// in the hand-written ports -- keeps each advance monotone and settles
// every course in one advance.  The least free room is found with a
// min-heap over the rooms occupied by pre[j].

import java.util.*;

public class LLPIntervalPartition {
  int n;
  int[][] pre;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((G[j] < leastFreeRoom(j)))) return false;
    return true;
  }

  private void advance() {
    G[j] = leastFreeRoom(j);
  }

  public int[] LLPIntervalPartition(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = 1;
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

  public int leastFreeRoom(int j) {
    java.util.PriorityQueue<int[]> h = new java.util.PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    for (int i : pre[j]) {
      h.add(new int[]{G[i], G[i]});
    }
    int r = 1;
    while (((!h.isEmpty()) && (h.peek()[1] == r))) {
      h.poll();
      r = (r + 1);
    }
    return r;
  }

  public static void main(String[] args) {
    // No runnable example: LLPIntervalPartition's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}