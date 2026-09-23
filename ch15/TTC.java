// Gale's Top Trading Cycle (TTC) algorithm for the housing market.
// At each stage, build the top-choice graph on unassigned agents,
// find a cycle in it, assign each agent in the cycle their current top
// remaining house, and mark them fixed.  Iterate until everyone is fixed.

import java.util.*;

public class TTC {
  public int[] assign(int[][] pref) {
    int n = pref.length;
    int[] house = new int[n];
    boolean[] fixed = new boolean[n];
    int[] G = new int[n];
    boolean[] onPath = new boolean[n];
    for (int i = 0; i < n; i++) {
      fixed[i] = false;
    }
    for (int i = 0; i < n; i++) {
      G[i] = 0;
    }
    int numFixed = 0;
    while ((numFixed < n)) {
      int i = 0;
      while ((i < n)) {
        if ((!fixed[i])) {
          while (fixed[pref[i][G[i]]]) {
            G[i] = (G[i] + 1);
          }
        }
        i = (i + 1);
      }
      int k = 0;
      while ((k < n)) {
        onPath[k] = false;
        k = (k + 1);
      }
      int start = 0;
      while (fixed[start]) {
        start = (start + 1);
      }
      int cur = start;
      onPath[cur] = true;
      int nxt = pref[cur][G[cur]];
      while ((!onPath[nxt])) {
        cur = nxt;
        onPath[cur] = true;
        nxt = pref[cur][G[cur]];
      }
      int cycleStart = nxt;
      cur = cycleStart;
      boolean cycled = false;
      while ((!cycled)) {
        int wish = pref[cur][G[cur]];
        house[cur] = wish;
        fixed[cur] = true;
        numFixed = (numFixed + 1);
        if ((wish == cycleStart)) {
          cycled = true;
        } else {
          cur = wish;
        }
      }
    }
    return house;
  }

  public static void main(String[] args) {
    int[][] pref = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    TTC prog = new TTC();
    int[] result = prog.assign(pref);
    System.out.println(Arrays.toString(result));
  }
}