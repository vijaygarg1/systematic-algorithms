// LLP-Prim: each non-root vertex advances when the global cross-cut is its lightest edge.
//
// setBase(1): every aux method here (minCrossCut, argMinCrossCut,
// globalMinCrossCut, propagateFixed) loops `i = 1; while (i <= n)`,
// a 1-indexed convention (index 0 reserved/unused) that was never
// declared -- without it, this.n = parent.length (0-indexed), so the
// very same loops read/write parent[n], one past the end of a
// length-n array. Declaring the 1-indexed base makes this.n =
// parent.length - 1, matching the loops as written (confirmed: this
// is the same setBase(1) convention LLP_StableMarriage uses for its
// own 1-indexed aux methods).

import java.util.*;

public class LLPPrim {
  int n;
  int[] parent;
  boolean[] fixed;
  double[][] W;
  double[] C;
  int root;
  int j;

  private boolean forbidden(int j) {
    if (fixed[j]) return false;
    if (!((argMinCrossCut(j) >= 1))) return false;
    if (!((minCrossCut(j) <= globalMinCrossCut()))) return false;
    if (!((C[j] < minCrossCut(j)))) return false;
    return true;
  }

  private void advance() {
    int i = argMinCrossCut(j);
    parent[j] = i;
    C[j] = W[i][j];
    propagateFixed();
  }

  public double[] LLPPrim(int[] parent, boolean[] fixed, double[][] W, double[] C, int root) {
    this.parent = parent;
    this.fixed = fixed;
    this.W = W;
    this.C = C;
    this.root = root;
    this.n = parent.length - 1;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 1; j <= n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return C;
  }

  public double minCrossCut(int j) {
    double best = Integer.MAX_VALUE;
    int i = 1;
    while ((i <= n)) {
      if ((fixed[i] && (W[i][j] < best))) {
        best = W[i][j];
      }
      i = (i + 1);
    }
    return best;
  }

  public int argMinCrossCut(int j) {
    int besti = (0 - 1);
    double best = Integer.MAX_VALUE;
    int i = 1;
    while ((i <= n)) {
      if ((fixed[i] && (W[i][j] < best))) {
        best = W[i][j];
        besti = i;
      }
      i = (i + 1);
    }
    return besti;
  }

  public double globalMinCrossCut() {
    double best = Integer.MAX_VALUE;
    int j = 1;
    while ((j <= n)) {
      if ((!fixed[j])) {
        double m = minCrossCut(j);
        if ((m < best)) {
          best = m;
        }
      }
      j = (j + 1);
    }
    return best;
  }

  public void propagateFixed() {
    boolean changed = true;
    while (changed) {
      changed = false;
      int j = 1;
      while ((j <= n)) {
        if (((!fixed[j]) && fixed[parent[j]])) {
          fixed[j] = true;
          changed = true;
        }
        j = (j + 1);
      }
    }
  }

  public static void main(String[] args) {
    int[] parent = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    boolean[] fixed = new boolean[] {false, false, false, false, false, false, false, false};
    double[][] W = new double[][] {{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0}, {9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0}, {17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0}, {25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0}, {33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0}, {41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0}, {49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0}, {57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0}};
    double[] C = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPPrim prog = new LLPPrim();
    double[] result = prog.LLPPrim(parent, fixed, W, C, 0);
    System.out.println(Arrays.toString(result));
  }
}