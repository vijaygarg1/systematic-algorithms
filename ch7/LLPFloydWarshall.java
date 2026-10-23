// Companion-only program: the book presents LLP-FloydWarshall only as
// LaTeX pseudocode (bxx-sp-llp-apsp.tex's algorithm box), never as a
// compilable .llp file -- there is no book/lang/progs-shortestPath/
// counterpart to sync from. See COMPANION_ONLY_STEMS in
// sync-code-from-lang.py: this file must be preserved there or a sync
// run will silently delete it (as happened once already).
//
// Matches the book's staged formulation (bxx-sp-llp-apsp.tex): the
// intermediate vertex k is processed in n sequential stages k = 0, ...,
// n-1, each stage realizing the book's ensure(i,j): G[i][j] <= G[i][k] +
// G[k][j] as a forbidden/advance pair over (i,j), rather than resolving
// `exists k`/`min k` over all k at once (the old, unordered "ensure" cost
// an extra factor of n -- see the book's complexity paragraph).
//
// Placement matters for the compiler: forbidden/advance owns its own
// (i,j) domain scan internally (a while(changed) fixpoint), so it must
// sit directly in the k-stage's body, NOT nested inside an explicit
// forall i / forall j of the same indices -- that would re-run the whole
// O(n^2) fixpoint once per (i,j) pair, i.e. O(n^5). Within a stage the
// fixpoint itself costs only two O(n^2) passes (one that applies updates,
// one that confirms none remain), since G[i][k] and G[k][j] are fixed
// for the duration of stage k (the book's correctness argument) -- so
// nothing cascades and the whole program is O(n^3), matching classical
// FloydWarshall.
//
// k must be declared as a field (a plain top-level decl, before any
// other statement) rather than as a `forall k in [0..n-1]` loop variable
// or a locally-scoped `int k = 0;` introduced after other statements --
// the compiler only promotes top-of-body declarations to instance
// fields, and forbidden/advance's generated helper methods can only see
// fields (and their own declared indices i, j), not enclosing-loop
// locals. An out-of-place `int k` here silently fails to compile with
// "cannot find symbol: k" inside the generated helpers.
//
// n is likewise bound explicitly for the same reason documented in
// LLPLexMatchVertexCover.llp: this program's only parameter is 2D, so
// there is no 1D-array-length fallback for the compiler's usual
// n-from-array-length inference.

import java.util.*;

public class LLPFloydWarshall {
  int n;
  int[][] A;
  int[][] G;
  int k;
  int i;
  int j;

  private boolean _forbidden0(int i, int j) {
    if (!((G[i][k] != Integer.MAX_VALUE))) return false;
    if (!((G[k][j] != Integer.MAX_VALUE))) return false;
    if (!((G[i][j] > (G[i][k] + G[k][j])))) return false;
    return true;
  }

  private void _advance0() {
    G[i][j] = (G[i][k] + G[k][j]);
  }

  public int[][] LLPFloydWarshall(int[][] A) {
    this.A = A;
    this.n = A.length;
    this.G = new int[n][n];
    this.k = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        G[i][j] = A[i][j];
      }
    }
    while ((k < n)) {
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
      k = (k + 1);
    }
    return G;
  }

  public static void main(String[] args) {
    int[][] A = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPFloydWarshall prog = new LLPFloydWarshall();
    int[][] result = prog.LLPFloydWarshall(A);
    System.out.println(Arrays.deepToString(result));
  }
}