// Companion-only program: the book presents LLP-FloydWarshall only as
// LaTeX pseudocode (bxx-sp-llp-apsp.tex's algorithm box), never as a
// compilable .llp file -- there is no book/lang/progs-shortestPath/
// counterpart to sync from. See COMPANION_ONLY_STEMS in
// sync-code-from-lang.py: this file must be preserved there or a sync
// run will silently delete it (as happened once already).
//
// forbidden(i,j): exists k: G[i][j] > G[i][k]+G[k][j]; advance:
// G[i][j] := min_k(G[i][k]+G[k][j]) -- faithful to the book's algorithm
// box. Weights are int (matching every other shortest-path program in
// this chapter) rather than double: llc.py's `min k in [..] : expr`
// quantifier always accumulates into an `int`, so a double-typed G would
// not compile (a real but narrow compiler gap -- nothing else in the
// corpus pairs a double[] state with this quantifier form). The initial
// G := A copy is written as an explicit forall rather than `G = A`
// one-line aliasing: llc.py currently lowers whole-array-to-array
// assignment incorrectly for 2D arrays (`G[u][v] = A;` instead of
// `= A[u][v];`), another narrow gap with no other users in the corpus.

import java.util.*;

public class LLPFloydWarshall {
  int n;
  int[][] A;
  int[][] G;
  int i;
  int j;

  private boolean _forbidden0(int i, int j) {
    boolean t1 = false;
    for (int k = 0; k < n; k++) {
      if ((G[i][j] > (G[i][k] + G[k][j]))) { t1 = true; break; }
    }
    return t1;
  }

  private void _advance0() {
    int m = Integer.MAX_VALUE;
    for (int k = 0; k < n; k++) m = Math.min(m, (G[i][k] + G[k][j]));
    G[i][j] = m;
  }

  public int[][] LLPFloydWarshall(int[][] A) {
    this.A = A;
    this.G = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        G[i][j] = A[i][j];
      }
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            if (_forbidden0(i, j)) {
              this.i = i; this.j = j; _advance0();
              changed = true;
            }
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[][] A = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    LLPFloydWarshall prog = new LLPFloydWarshall();
    int[][] result = prog.LLPFloydWarshall(A);
    System.out.println(Arrays.deepToString(result));
  }
}