// Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements.
//
// Follows Algorithm ApproxSetCover in bxx-approximate.tex, including its
// stated precondition: the instance is assumed feasible, i.e. every
// element of the universe lies in some set, so that a cover exists. The
// loop guard is the book's `R != empty`, not a weaker "some set still
// covers something new" test -- on an infeasible instance it would not
// terminate, exactly as the book's algorithm would not.

import java.util.*;

public class ApproxSetCover {
  public boolean[] ApproxSetCover(int[][] S, int n) {
    int m = S.length;
    boolean[] C = new boolean[m];
    boolean[] covered = new boolean[n];
    while (anyUncovered(covered, n)) {
      int bestIdx = 0;
      int bestCover = (0 - 1);
      for (int s = 0; s < m; s++) {
        {
          int count = 0;
          int e = 0;
          while ((e < n)) {
            if (((S[s][e] == 1) && (!covered[e]))) {
              count = (count + 1);
            }
            e = (e + 1);
          }
          if ((count > bestCover)) {
            bestCover = count;
            bestIdx = s;
          }
        }
      }
      C[bestIdx] = true;
      for (int e = 0; e < n; e++) {
        {
          if ((S[bestIdx][e] == 1)) {
            covered[e] = true;
          }
        }
      }
    }
    return C;
  }

  public boolean anyUncovered(boolean[] covered, int n) {
    boolean t1 = false;
    for (int e = 0; e < n; e++) {
      if ((!covered[e])) { t1 = true; break; }
    }
    return t1;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int n = 0;
    ApproxSetCover prog = new ApproxSetCover();
    boolean[] result = prog.ApproxSetCover(S, n);
    System.out.println(Arrays.toString(result));
  }
}