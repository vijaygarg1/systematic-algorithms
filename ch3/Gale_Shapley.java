// Classical Gale-Shapley man-optimal stable-matching loop.

import java.util.*;

public class Gale_Shapley {
  public int[] match(int[][] mpref, int[][] rank) {
    int n = (mpref.length - 1);
    int[] G = new int[(n + 1)];
    int[] partner = new int[(n + 1)];
    boolean[] free = new boolean[(n + 1)];
    for (int i = 1; i <= n; i++) {
      free[i] = true;
    }
    boolean done = false;
    while ((!done)) {
      int i = 1;
      while (((i <= n) && (!free[i]))) {
        i = (i + 1);
      }
      if ((i > n)) {
        done = true;
      } else {
        G[i] = (G[i] + 1);
        int z = mpref[i][G[i]];
        if ((partner[z] == 0)) {
          partner[z] = i;
          free[i] = false;
        } else {
          if ((rank[z][i] < rank[z][partner[z]])) {
            free[partner[z]] = true;
            partner[z] = i;
            free[i] = false;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    Gale_Shapley prog = new Gale_Shapley();
    int[] result = prog.match(mpref, rank);
    System.out.println(Arrays.toString(result));
  }
}