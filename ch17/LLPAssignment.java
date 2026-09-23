// LLP assignment: minimum clearing price vector via step-jump price increments.
// Each iteration: identify overdemanded items, raise each by step[j] = min slack
// to the next critical price (the smallest amount that lets some bidder become
// indifferent and break a tight edge). Strongly polynomial.

import java.util.*;

public class LLPAssignment {
  public int[] LLPAssignment(int[][] v) {
    int n = v[0].length;
    int m = v.length;
    int[] C = new int[n];
    for (int k = 0; k < n; k++) {
      C[k] = 0;
    }
    boolean done = false;
    while ((!done)) {
      boolean hasMatching = checkPerfectMatching(v, C);
      if (hasMatching) {
        done = true;
      } else {
        raiseOverdemandedPrices(v, C);
      }
    }
    return C;
  }

  public boolean checkPerfectMatching(int[][] v, int[] C) {
    int n = C.length;
    int m = v.length;
    int[] partner = new int[n];
    for (int k = 0; k < n; k++) {
      partner[k] = (0 - 1);
    }
    int matched = 0;
    int b = 0;
    while ((b < m)) {
      boolean[] seen = new boolean[n];
      if (tryMatch(b, v, C, partner, seen)) {
        matched = (matched + 1);
      }
      b = (b + 1);
    }
    return (matched == m);
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

  public void raiseOverdemandedPrices(int[][] v, int[] C) {
    int n = C.length;
    int m = v.length;
    // Snapshot bestSurplus[b] for every bidder before any prices change this round.
    int[] bestSurplus = new int[m];
    int b = 0;
    while ((b < m)) {
      bestSurplus[b] = (0 - 2147483647);
      int i = 0;
      while ((i < n)) {
        int s = (v[b][i] - C[i]);
        if ((s > bestSurplus[b])) {
          bestSurplus[b] = s;
        }
        i = (i + 1);
      }
      b = (b + 1);
    }
    // For each item j: count demand[j] and the per-item step (min slack across
    // bidders whose top choice includes j). step[j] is how far we can raise
    // C[j] before some bidder b becomes indifferent to another item.
    int[] step = new int[n];
    int[] demand = new int[n];
    int j = 0;
    while ((j < n)) {
      step[j] = 2147483647;
      demand[j] = 0;
      b = 0;
      while ((b < m)) {
        if (((v[b][j] - C[j]) == bestSurplus[b])) {
          demand[j] = (demand[j] + 1);
          int secondBest = (0 - 2147483647);
          int i = 0;
          while ((i < n)) {
            if ((i != j)) {
              int s = (v[b][i] - C[i]);
              if ((s > secondBest)) {
                secondBest = s;
              }
            }
            i = (i + 1);
          }
          int slack = ((v[b][j] - C[j]) - secondBest);
          if ((slack < step[j])) {
            step[j] = slack;
          }
        }
        b = (b + 1);
      }
      // Integer arithmetic: a tied bidder gives slack 0; raise by at least 1.
      if ((step[j] < 1)) {
        step[j] = 1;
      }
      j = (j + 1);
    }
    // Raise every overdemanded item simultaneously.
    j = 0;
    while ((j < n)) {
      if ((demand[j] > 1)) {
        C[j] = (C[j] + step[j]);
      }
      j = (j + 1);
    }
  }

  public static void main(String[] args) {
    int[][] v = new int[][] {{5, 3, 1}, {4, 4, 2}, {1, 2, 5}};
    LLPAssignment prog = new LLPAssignment();
    int[] result = prog.LLPAssignment(v);
    System.out.println(Arrays.toString(result));
  }
}
