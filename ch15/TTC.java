// Gale's Top Trading Cycle (TTC) algorithm for the housing market,
// matching bxx-housing.tex Algorithm TTC exactly: each stage (1) every
// unfixed agent advances its wish pointer past houses already owned by
// fixed agents, building the top-choice graph; (2) ALL node-disjoint
// cycles in that graph are found at once (every unfixed node has
// out-degree 1, so each agent either lies on a cycle or on a tail
// leading into one); (3) every agent on ANY cycle this stage trades
// and is fixed -- not a single pointer-chasing walk that resolves one
// cycle at a time and rebuilds the whole graph for each one.

import java.util.*;

public class TTC {
  public int[] assign(int[][] pref) {
    int n = pref.length;
    int[] house = new int[n];
    boolean[] fixed = new boolean[n];
    int[] G = new int[n];
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
      int[] state = new int[n];
      boolean[] inCycle = new boolean[n];
      int[] path = new int[n];
      i = 0;
      while ((i < n)) {
        if (((!fixed[i]) && (state[i] == 0))) {
          int pathLen = 0;
          int cur = i;
          while ((state[cur] == 0)) {
            state[cur] = 1;
            path[pathLen] = cur;
            pathLen = (pathLen + 1);
            cur = pref[cur][G[cur]];
          }
          if ((state[cur] == 1)) {
            int p = 0;
            boolean onCyc = false;
            while ((p < pathLen)) {
              if ((path[p] == cur)) {
                onCyc = true;
              }
              if (onCyc) {
                inCycle[path[p]] = true;
              }
              p = (p + 1);
            }
          }
          int p2 = 0;
          while ((p2 < pathLen)) {
            state[path[p2]] = 2;
            p2 = (p2 + 1);
          }
        }
        i = (i + 1);
      }
      i = 0;
      while ((i < n)) {
        if (inCycle[i]) {
          house[i] = pref[i][G[i]];
          fixed[i] = true;
          numFixed = (numFixed + 1);
        }
        i = (i + 1);
      }
    }
    return house;
  }

  public static void main(String[] args) {
    int[][] pref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    TTC prog = new TTC();
    int[] result = prog.assign(pref);
    System.out.println(Arrays.toString(result));
  }
}