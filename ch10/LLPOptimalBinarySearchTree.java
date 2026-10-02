// LLP-OptimalBinarySearchTree: ensure-based formulation with interval priority.
// G[j] encodes the flattened (lo, hi) interval cost: lo = j / n, hi = j % n.

import java.util.*;

public class LLPOptimalBinarySearchTree {
  int n;
  double[] p;
  double[] G;

  private boolean forbidden(int j) {
    if ((G[j] >= optCost(p, G, (j / p.length), (j % p.length)))) return false;
    return true;
  }

  private void advance(int j) {
    G[j] = optCost(p, G, (j / p.length), (j % p.length));
  }

  public void LLPOptimalBinarySearchTree(double[] p, double[] G) {
    this.p = p;
    this.G = G;
    this.n = p.length;
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
  }

  public double optCost(double[] p, double[] G, int lo, int hi) {
    if ((lo > hi)) {
      return 0.0;
    }
    double s = 0.0;
    int l = lo;
    while ((l <= hi)) {
      s = (s + p[l]);
      l = (l + 1);
    }
    int n = p.length;
    double best = Integer.MAX_VALUE;
    int k = lo;
    while ((k <= hi)) {
      double left = 0.0;
      double right = 0.0;
      if ((k > lo)) {
        left = G[(((lo * n) + k) - 1)];
      }
      if ((k < hi)) {
        right = G[(((k + 1) * n) + hi)];
      }
      double cost = ((s + left) + right);
      if ((cost < best)) {
        best = cost;
      }
      k = (k + 1);
    }
    return best;
  }

  public static void main(String[] args) {
    double[] p = new double[] {1.0, 2.0, 3.0, 4.0};
    double[] G = new double[] {1.0, 2.0, 3.0, 4.0};
    LLPOptimalBinarySearchTree prog = new LLPOptimalBinarySearchTree();
    prog.LLPOptimalBinarySearchTree(p, G);
    System.out.println(Arrays.toString(p));
    System.out.println(Arrays.toString(G));
  }
}