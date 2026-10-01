// LLP assignment: minimum clearing price vector, matching
// bxx-assignment.tex Algorithm LLP-Assignment. When no perfect matching
// exists in the current tight-edge graph, find an inclusion-minimal
// overdemanded set J via alternating-path reachability from an
// unmatched bidder, then raise every item in J by ONE SHARED amount
// delta = min over bidders b demanding into J of [bidder b's best
// surplus - bidder b's best surplus using an item outside J].
//
// Earlier version of this file (kept for reference, not used): raised
// each overdemanded item independently, using "second-best surplus
// excluding only that one item" instead of "excluding the whole
// overdemanded set J" -- a materially different (and, when J has more
// than one item, much slower) mechanism, since it degrades to
// unit-increment steps whenever excluding a single item still leaves
// another same-value item available. It still converges to the correct
// clearing price, just far more slowly than the book's batched-delta
// jump.
//
// class LLPAssignment {
// int[] LLPAssignment(int[][] v) {
// int n = v[0].length;
// int m = v.length;
// int[] C = new int[n];
// forall k in [0..n-1] : C[k] = 0;
// boolean done = false;
// while (!done) {
// boolean hasMatching = checkPerfectMatching(v, C);
// if (hasMatching) {
// done = true;
// } else {
// raiseOverdemandedPrices(v, C);
// }
// };
// return C;
// }
//
// boolean checkPerfectMatching(int[][] v, int[] C) {
// int n = C.length;
// int m = v.length;
// int[] partner = new int[n];
// forall k in [0..n-1] : partner[k] = 0 - 1;
// int matched = 0;
// int b = 0;
// while (b < m) {
// boolean[] seen = new boolean[n];
// if (tryMatch(b, v, C, partner, seen)) {
// matched = matched + 1;
// };
// b = b + 1;
// };
// return matched == m;
// }
//
// boolean tryMatch(int b, int[][] v, int[] C, int[] partner, boolean[] seen) {
// int n = C.length;
// int bestSurplus = 0 - 2147483647;
// int i = 0;
// while (i < n) {
// int s = v[b][i] - C[i];
// if (s > bestSurplus) {
// bestSurplus = s;
// };
// i = i + 1;
// };
// i = 0;
// while (i < n) {
// if (v[b][i] - C[i] == bestSurplus && !seen[i]) {
// seen[i] = true;
// if (partner[i] == 0 - 1 || tryMatch(partner[i], v, C, partner, seen)) {
// partner[i] = b;
// return true;
// }
// };
// i = i + 1;
// };
// return false;
// }
//
// void raiseOverdemandedPrices(int[][] v, int[] C) {
// int n = C.length;
// int m = v.length;
// int[] bestSurplus = new int[m];
// int b = 0;
// while (b < m) {
// bestSurplus[b] = 0 - 2147483647;
// int i = 0;
// while (i < n) {
// int s = v[b][i] - C[i];
// if (s > bestSurplus[b]) {
// bestSurplus[b] = s;
// };
// i = i + 1;
// };
// b = b + 1;
// };
// int[] step = new int[n];
// int[] demand = new int[n];
// int j = 0;
// while (j < n) {
// step[j] = 2147483647;
// demand[j] = 0;
// b = 0;
// while (b < m) {
// if (v[b][j] - C[j] == bestSurplus[b]) {
// demand[j] = demand[j] + 1;
// int secondBest = 0 - 2147483647;
// int i = 0;
// while (i < n) {
// if (i != j) {
// int s = v[b][i] - C[i];
// if (s > secondBest) {
// secondBest = s;
// }
// };
// i = i + 1;
// };
// int slack = (v[b][j] - C[j]) - secondBest;
// if (slack < step[j]) {
// step[j] = slack;
// }
// };
// b = b + 1;
// };
// if (step[j] < 1) {
// step[j] = 1;
// };
// j = j + 1;
// };
// j = 0;
// while (j < n) {
// if (demand[j] > 1) {
// C[j] = C[j] + step[j];
// };
// j = j + 1;
// }
// }
// }

import java.util.*;

public class LLPAssignment {
  public int[] LLPAssignment(int[][] v) {
    int n = v[0].length;
    int m = v.length;
    int[] C = new int[n];
    for (int k = 0; k < n; k++) {
      C[k] = 0;
    }
    int[] partner = new int[n];
    boolean done = false;
    while ((!done)) {
      for (int k = 0; k < n; k++) {
        partner[k] = (0 - 1);
      }
      int b = 0;
      while ((b < m)) {
        boolean[] seen = new boolean[n];
        tryMatch(b, v, C, partner, seen);
        b = (b + 1);
      }
      boolean[] bidderMatched = new boolean[m];
      int i = 0;
      while ((i < n)) {
        if ((partner[i] != (0 - 1))) {
          bidderMatched[partner[i]] = true;
        }
        i = (i + 1);
      }
      int unmatched = (0 - 1);
      b = 0;
      while (((b < m) && (unmatched == (0 - 1)))) {
        if ((!bidderMatched[b])) {
          unmatched = b;
        }
        b = (b + 1);
      }
      if ((unmatched == (0 - 1))) {
        done = true;
      } else {
        boolean[] itemInJ = new boolean[n];
        boolean[] bidderInB = new boolean[m];
        reach(unmatched, v, C, partner, itemInJ, bidderInB);
        int delta = 2147483647;
        b = 0;
        while ((b < m)) {
          if (bidderInB[b]) {
            int best = bestSurplus(b, v, C);
            int bestOutside = bestSurplusOutside(b, v, C, itemInJ);
            int gap = (best - bestOutside);
            if ((gap < delta)) {
              delta = gap;
            }
          }
          b = (b + 1);
        }
        int j = 0;
        while ((j < n)) {
          if (itemInJ[j]) {
            C[j] = (C[j] + delta);
          }
          j = (j + 1);
        }
      }
    }
    return C;
  }

  public int bestSurplus(int b, int[][] v, int[] C) {
    int n = C.length;
    int best = (0 - 2147483647);
    int i = 0;
    while ((i < n)) {
      int s = (v[b][i] - C[i]);
      if ((s > best)) {
        best = s;
      }
      i = (i + 1);
    }
    return best;
  }

  public int bestSurplusOutside(int b, int[][] v, int[] C, boolean[] itemInJ) {
    int n = C.length;
    int best = (0 - 2147483647);
    int i = 0;
    while ((i < n)) {
      if ((!itemInJ[i])) {
        int s = (v[b][i] - C[i]);
        if ((s > best)) {
          best = s;
        }
      }
      i = (i + 1);
    }
    return best;
  }

  public void reach(int b, int[][] v, int[] C, int[] partner, boolean[] itemInJ, boolean[] bidderInB) {
    if ((!bidderInB[b])) {
      bidderInB[b] = true;
      int n = C.length;
      int best = bestSurplus(b, v, C);
      int i = 0;
      while ((i < n)) {
        if ((((v[b][i] - C[i]) == best) && (!itemInJ[i]))) {
          itemInJ[i] = true;
          if ((partner[i] != (0 - 1))) {
            reach(partner[i], v, C, partner, itemInJ, bidderInB);
          }
        }
        i = (i + 1);
      }
    }
  }

  public boolean tryMatch(int b, int[][] v, int[] C, int[] partner, boolean[] seen) {
    int n = C.length;
    int bestSurplus = (0 - 2147483647);
    int i = 0;
    while ((i < n)) {
      int s = (v[b][i] - C[i]);
      if ((s > bestSurplus)) {
        bestSurplus = s;
      }
      i = (i + 1);
    }
    i = 0;
    while ((i < n)) {
      if ((((v[b][i] - C[i]) == bestSurplus) && (!seen[i]))) {
        seen[i] = true;
        if (((partner[i] == (0 - 1)) || tryMatch(partner[i], v, C, partner, seen))) {
          partner[i] = b;
          return true;
        }
      }
      i = (i + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] v = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    LLPAssignment prog = new LLPAssignment();
    int[] result = prog.LLPAssignment(v);
    System.out.println(Arrays.toString(result));
  }
}