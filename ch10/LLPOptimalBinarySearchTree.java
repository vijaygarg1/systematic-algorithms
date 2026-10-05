// LLP-OptimalBinarySearchTree: ensure G[i][j] >= optimal cost over interval [i..j].

import java.util.*;

public class LLPOptimalBinarySearchTree {
  int n;
  double[] p;
  double[][] G;
  int i;
  int j;

  private boolean forbidden(int i, int j) {
    if (!((G[i][j] < optCost(p, G, i, j)))) return false;
    return true;
  }

  private void advance() {
    G[i][j] = optCost(p, G, i, j);
  }

  public void LLPOptimalBinarySearchTree(double[] p, double[][] G) {
    this.p = p;
    this.G = G;
    this.n = p.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            if (forbidden(i, j)) {
              this.i = i; this.j = j; advance();
              changed = true;
            }
          }
        }
      }
    }
  }

  public double optCost(double[] p, double[][] G, int i, int j) {
    double best = Integer.MAX_VALUE;
    int k = i;
    while ((k <= j)) {
      double s = 0.0;
      int l = i;
      while ((l <= j)) {
        s = (s + p[l]);
        l = (l + 1);
      }
      double leftCost = 0.0;
      if ((k > i)) {
        leftCost = G[i][(k - 1)];
      }
      double rightCost = 0.0;
      if ((k < j)) {
        rightCost = G[(k + 1)][j];
      }
      double cost = ((leftCost + s) + rightCost);
      if ((cost < best)) {
        best = cost;
      }
      k = (k + 1);
    }
    return best;
  }

  public static void main(String[] args) {
    double[] p = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double[][] G = new double[][] {{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0}, {9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0}, {17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0}, {25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0}, {33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0}, {41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0}, {49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0}, {57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0}};
    LLPOptimalBinarySearchTree prog = new LLPOptimalBinarySearchTree();
    prog.LLPOptimalBinarySearchTree(p, G);
    System.out.println(Arrays.toString(p));
    System.out.println(Arrays.deepToString(G));
  }
}