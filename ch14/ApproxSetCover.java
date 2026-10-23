// Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements.
//
// Follows Algorithm ApproxSetCover in bxx-approximate.tex, including its
// stated precondition: the instance is assumed feasible, i.e. every
// element of the universe lies in some set, so that a cover exists. The
// loop guard is the book's `R != empty`, not a weaker "some set still
// covers something new" test -- on an infeasible instance it would not
// terminate, exactly as the book's algorithm would not.
//
// The uncovered counts cnt[s] = |s ∩ R| are maintained instead of being
// recomputed each round: covering an element decrements the count of every
// set containing it, so the total number of updates over the whole run is
// one per (set, element) incidence. Recomputing all m counts in each of the
// up-to min(m,n) rounds costs a factor min(m,n) more.

import java.util.*;

public class ApproxSetCover {
  public boolean[] ApproxSetCover(int[][] S, int n) {
    int m = S.length;
    boolean[] C = new boolean[m];
    boolean[] covered = new boolean[n];
    int[] cnt = new int[m];
    int remaining = n;
    for (int s = 0; s < m; s++) {
      int t1 = 0;
      for (int e = 0; e < n; e++) {
        if ((S[s][e] == 1)) t1++;
      }
      cnt[s] = t1;
    }
    while ((remaining > 0)) {
      int bestIdx = 0;
      int bestCover = (0 - 1);
      int s = 0;
      while ((s < m)) {
        if ((cnt[s] > bestCover)) {
          bestCover = cnt[s];
          bestIdx = s;
        }
        s = (s + 1);
      }
      C[bestIdx] = true;
      int e = 0;
      while ((e < n)) {
        if (((S[bestIdx][e] == 1) && (!covered[e]))) {
          covered[e] = true;
          remaining = (remaining - 1);
          int t = 0;
          while ((t < m)) {
            if ((S[t][e] == 1)) {
              cnt[t] = (cnt[t] - 1);
            }
            t = (t + 1);
          }
        }
        e = (e + 1);
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int n = 0;
    ApproxSetCover prog = new ApproxSetCover();
    boolean[] result = prog.ApproxSetCover(S, n);
    System.out.println(Arrays.toString(result));
  }
}