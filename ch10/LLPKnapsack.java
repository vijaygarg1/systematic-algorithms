// LLP-Knapsack: ensure G[i,c] >= max(G[i-1,c], G[i-1,c-w[i]]+v[i]) via two
// forbidden/advance rules over the full (i,c) lattice, matching
// bxx-dynamicProgramming.tex Algorithm LLP-Knapsack:
// Forbidden(i,c): G[i,c] < G[i-1,c]                        Advance: G[i,c] := G[i-1,c]
// Forbidden(i,c): (w_i<=c) && G[i,c] < G[i-1,c-w_i]+v_i     Advance: G[i,c] := G[i-1,c-w_i]+v_i
// Both rules share the index pair (i,c), but i ranges over items (1..n)
// while c ranges over capacities (0..W) -- a different bound than n, which
// the compiler's forbidden(...) construct can't express (it assumes every
// guard index shares one bound). So this scans to a fixpoint explicitly,
// compiling to exactly the boolean-changed/while(changed) shape the native
// construct would generate for a single shared bound.
//
// Previous version of this file (kept for reference, not used): the
// book's own simplified incremental step, which this (correctly) models
// as a single-index forbidden(j) lifting one previous row C[] to admit
// one new item -- but that step algorithm, and the driver loop that calls
// it once per item, are both part of the ISPARBOOK-only presentation in
// bxx-dynamicProgramming.tex and never render in the sequential book. The
// sequential book's actual (and only) "LLP-Knapsack" is the direct
// forbidden(i,c) algorithm below.
//
// class LLPIncrKnapsack {
// int[] LLPIncrKnapsack(int w, int v, int[] C) {
// int[] G = 0;
// forbidden (j) :
// G[j] < C[j] || (j >= w && G[j] < C[j - w] + v)
// =>
// advance :
// G[j] = newValue(j);
// return G;
// }
//
// // The pointwise maximum of "skip the new item" (C[j]) and
// // "take the new item" (C[j - w] + v, only if j >= w).
// int newValue(int j) {
// int skip = C[j];
// if (j >= w) {
// int take = C[j - w] + v;
// if (take > skip) {
// return take;
// }
// };
// return skip;
// }
// }

import java.util.*;

public class LLPKnapsack {
  public int[][] LLPKnapsack(int[] w, int[] v, int W) {
    int n = (w.length - 1);
    int[][] G = new int[(n + 1)][(W + 1)];
    for (int c = 0; c <= W; c++) {
      G[0][c] = 0;
    }
    for (int i = 0; i <= n; i++) {
      G[i][0] = 0;
    }
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int i = 1; i <= n; i++) {
        for (int c = 1; c <= W; c++) {
          {
            if ((G[i][c] < G[(i - 1)][c])) {
              G[i][c] = G[(i - 1)][c];
              changed = true;
            }
            if (((w[i] <= c) && (G[i][c] < (G[(i - 1)][(c - w[i])] + v[i])))) {
              G[i][c] = (G[(i - 1)][(c - w[i])] + v[i]);
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