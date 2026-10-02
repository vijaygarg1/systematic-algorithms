// LLP single-item knapsack step: lift previous-row values to admit one new item.

import java.util.*;

public class LLPIncrKnapsack {
  int n;
  int w;
  int v;
  int[] C;
  int[] G;

  private boolean forbidden(int j) {
    if (!(((G[j] < C[j]) || ((j >= w) && (G[j] < (C[(j - w)] + v)))))) return false;
    return true;
  }

  private void advance(int j) {
    G[j] = newValue(j);
  }

  public int[] LLPIncrKnapsack(int w, int v, int[] C) {
    this.w = w;
    this.v = v;
    this.C = C;
    this.n = C.length;
    this.G = new int[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            advance(j);
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public int newValue(int j) {
    int skip = C[j];
    if ((j >= w)) {
      int take = (C[(j - w)] + v);
      if ((take > skip)) {
        return take;
      }
    }
    return skip;
  }

  public static void main(String[] args) {
    int w = 3;
    int v = 5;
    int[] C = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPIncrKnapsack prog = new LLPIncrKnapsack();
    int[] result = prog.LLPIncrKnapsack(w, v, C);
    System.out.println(Arrays.toString(result));
  }
}