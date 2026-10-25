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
//
// Written as the book's `ensure`, not as the forbidden/advance pair it
// desugars to, matching Algorithm LLP-FloydWarshall:
// for k := 1 to n:
// ensure (i,j in [1..n]): G[i,j] <= G[i,k] + G[k,j]
//
// G is double, as the book's "matrix of real" says. That is what lets the
// ensure stand bare: an unreachable pair holds infinity, and IEEE gives
// infinity + infinity = infinity, so the guard is false there. With int
// the same sum overflows MAX_VALUE to a large negative and the guard
// fires on unreachable pairs -- the defect LLPBFS.llp had, which is why
// the int version needed explicit `!= infinity` tests.
//
// G stays a matrix, and the book keeps brackets for it: all-pairs output
// is Theta(n^2) by definition. Only the edge weight is the accessor
// w(i,j) -- see the note in the chapter-7 Dijkstra section.

import java.util.*;

public class LLPFloydWarshall {
  int n;
  double[][] A;
  int k;
  double[][] G;
  int i;
  int j;

  private boolean _forbidden0(int i, int j) {
    if ((G[i][j] <= (G[i][k] + G[k][j]))) return false;
    return true;
  }

  private void _advance0() {
    G[i][j] = (G[i][k] + G[k][j]);
  }

  public double[][] LLPFloydWarshall(double[][] A) {
    this.A = A;
    this.k = 0;
    this.n = A.length;
    this.G = new double[n][n];
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
    double[][] A = new double[][] {{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0}, {9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0}, {17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0}, {25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0}, {33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0}, {41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0}, {49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0}, {57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0}};
    LLPFloydWarshall prog = new LLPFloydWarshall();
    double[][] result = prog.LLPFloydWarshall(A);
    System.out.println(Arrays.deepToString(result));
  }
}