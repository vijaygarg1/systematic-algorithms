// 0/1 knapsack: O(nW) bottom-up table fill.

import java.util.*;

public class Knapsack {
  public int[][] Knapsack(int[] w, int[] v, int W) {
    int n = (w.length - 1);
    int[][] G = new int[(n + 1)][(W + 1)];
    for (int c = 0; c <= W; c++) {
      G[0][c] = 0;
    }
    for (int i = 0; i <= n; i++) {
      G[i][0] = 0;
    }
    for (int i = 1; i <= n; i++) {
      for (int c = 1; c <= W; c++) {
        if ((w[i] > c)) {
          G[i][c] = G[(i - 1)][c];
        } else {
          G[i][c] = Math.max(G[(i - 1)][c], (G[(i - 1)][(c - w[i])] + v[i]));
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] w = new int[] {0, 0, 1, 2};
    int[] v = new int[] {0, 0, 1, 1};
    int W = 10;
    Knapsack prog = new Knapsack();
    int[][] result = prog.Knapsack(w, v, W);
    System.out.println(Arrays.deepToString(result));
  }
}