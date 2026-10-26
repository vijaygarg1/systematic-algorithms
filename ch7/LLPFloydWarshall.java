// Companion-only: book shows LLP-FloydWarshall only as LaTeX pseudocode,
// no .llp counterpart to sync from -- see COMPANION_ONLY_STEMS in
// sync-code-from-lang.py or a sync run will delete this file.
// k stages the intermediate vertex 0..n-1; each stage's forbidden/advance
// (i,j) fixpoint must sit directly in the k-stage body, not nested inside

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