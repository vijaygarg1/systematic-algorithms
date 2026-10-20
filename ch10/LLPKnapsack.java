// LLP-Knapsack: the book's two rules over the (i, c) lattice, joined by
// `&&` so they share one fixpoint, with each index carrying its own
// bound -- i over items, c over capacities.

import java.util.*;

public class LLPKnapsack {
  int n;
  int[] w;
  int[] v;
  int W;
  int[][] G;
  int i;
  int c;

  private boolean _forbidden0(int i, int c) {
    if (!((G[i][c] < G[(i - 1)][c]))) return false;
    return true;
  }

  private void _advance0() {
    G[i][c] = G[(i - 1)][c];
  }

  private boolean _forbidden1(int i, int c) {
    if (!((w[i] <= c))) return false;
    if (!((G[i][c] < (G[(i - 1)][(c - w[i])] + v[i])))) return false;
    return true;
  }

  private void _advance1() {
    G[i][c] = (G[(i - 1)][(c - w[i])] + v[i]);
  }

  public int[][] LLPKnapsack(int[] w, int[] v, int W) {
    this.w = w;
    this.v = v;
    this.W = W;
    this.n = (w.length - 1);
    this.G = new int[(n + 1)][(W + 1)];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 1; i <= n; i++) {
          for (int c = 1; c <= W; c++) {
            if (_forbidden0(i, c)) {
              this.i = i; this.c = c; _advance0();
              changed = true;
            }
            if (_forbidden1(i, c)) {
              this.i = i; this.c = c; _advance1();
              changed = true;
            }
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] w = new int[] {0, 0, 1, 2};
    int[] v = new int[] {0, 0, 1, 1};
    int W = 10;
    LLPKnapsack prog = new LLPKnapsack();
    int[][] result = prog.LLPKnapsack(w, v, W);
    System.out.println(Arrays.deepToString(result));
  }
}