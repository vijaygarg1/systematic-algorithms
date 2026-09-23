// LLP-Prim: each non-root vertex advances when the global cross-cut is its lightest edge.

import java.util.*;

public class LLPPrim {
  int n;
  int[] parent;
  boolean[] fixed;
  double[][] W;
  double[] C;
  int root;

  private boolean forbidden(int j) {
    if (fixed[j]) return false;
    if (!((argMinCrossCut(j) >= 1))) return false;
    if (!((minCrossCut(j) <= globalMinCrossCut()))) return false;
    if (!((C[j] < minCrossCut(j)))) return false;
    return true;
  }

  private void advance(int j) {
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
    this.n = parent.length;
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
    int[] parent = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    boolean[] fixed = new boolean[] {false, false, false, false};
    double[][] W = new double[][] {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};
    double[] C = new double[] {1.0, 2.0, 3.0, 4.0};
    LLPPrim prog = new LLPPrim();
    double[] result = prog.LLPPrim(parent, fixed, W, C, 0);
    System.out.println(Arrays.toString(result));
  }
}