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
//
// The instance is a pair of incidence lists: mem[s] holds the elements of
// set s and inc[e] holds the sets containing element e. Neither is an
// m x u matrix, so the whole instance is O(N) -- N being the number of
// (set, element) incidences -- rather than Theta(m*u). Every loop below
// walks one of these lists, so each pass costs O(N) and not O(m*u).
//
// Taking them as input rather than building them from a matrix is what
// makes that real: an m x u matrix had to be read once to build the lists,
// which is Theta(m*u) on its own and reached a gigabyte at n = 16000.

import java.util.*;

public class ApproxSetCover {
  public boolean[] ApproxSetCover(int[][] mem, int[][] inc) {
    int m = mem.length;
    int n = inc.length;
    boolean[] C = new boolean[m];
    boolean[] covered = new boolean[n];
    int[] cnt = new int[m];
    int remaining = n;
    for (int s = 0; s < m; s++) {
      cnt[s] = mem[s].length;
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
      int k = 0;
      while ((k < mem[bestIdx].length)) {
        int e = mem[bestIdx][k];
        if ((!covered[e])) {
          covered[e] = true;
          remaining = (remaining - 1);
          int t = 0;
          while ((t < inc[e].length)) {
            cnt[inc[e][t]] = (cnt[inc[e][t]] - 1);
            t = (t + 1);
          }
        }
        k = (k + 1);
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] mem = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] inc = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    ApproxSetCover prog = new ApproxSetCover();
    boolean[] result = prog.ApproxSetCover(mem, inc);
    System.out.println(Arrays.toString(result));
  }
}