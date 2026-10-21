// Classical divide-and-conquer closest pair of points.
//
// Px and Py hold the coordinates, indexed by point number, with the points
// numbered in increasing x order. Ys lists this subproblem's point numbers
// in increasing y order, which is what lets the strip scan stop after a
// constant number of neighbours. G is the running answer: G[0] the best
// SQUARED distance (squared, so no sqrt is ever needed), G[1] and G[2] the
// two points achieving it.

import java.util.*;

public class ClosestPair {
  public double[] find(int lo, int hi, double[] Px, double[] Py, int[] Ys) {
    double[] G = new double[3];
    G[0] = Double.POSITIVE_INFINITY;
    G[1] = (0 - 1);
    G[2] = (0 - 1);
    ClosestPair(lo, hi, Px, Py, Ys, G);
    return G;
  }

  public void ClosestPair(int lo, int hi, double[] Px, double[] Py, int[] Ys, double[] G) {
    if ((hi <= lo)) {
      return;
    }
    if (((hi - lo) <= 2)) {
      for (int i = lo; i < hi; i++) {
        {
          int j = (i + 1);
          while ((j <= hi)) {
            check(i, j, Px, Py, G);
            j = (j + 1);
          }
        }
      }
      return;
    }
    int mid = ((lo + hi) / 2);
    double midX = Px[mid];
    int n = ((hi - lo) + 1);
    int[] Ls = new int[n];
    int[] Rs = new int[n];
    int nl = 0;
    int nr = 0;
    int k = 0;
    while ((k < n)) {
      int p = Ys[k];
      if ((p <= mid)) {
        Ls[nl] = p;
        nl = (nl + 1);
      } else {
        Rs[nr] = p;
        nr = (nr + 1);
      }
      k = (k + 1);
    }
    ClosestPair(lo, mid, Px, Py, Ls, G);
    ClosestPair((mid + 1), hi, Px, Py, Rs, G);
    int[] strip = new int[n];
    int m = 0;
    k = 0;
    while ((k < n)) {
      int p = Ys[k];
      double dx = (Px[p] - midX);
      if (((dx * dx) < G[0])) {
        strip[m] = p;
        m = (m + 1);
      }
      k = (k + 1);
    }
    for (int a = 0; a < m; a++) {
      {
        int b = (a + 1);
        int limit = (a + 15);
        while (((b < m) && (b <= limit))) {
          check(strip[a], strip[b], Px, Py, G);
          b = (b + 1);
        }
      }
    }
  }

  public void check(int i, int j, double[] Px, double[] Py, double[] G) {
    double dx = (Px[i] - Px[j]);
    double dy = (Py[i] - Py[j]);
    double d = ((dx * dx) + (dy * dy));
    if ((d < G[0])) {
      G[0] = d;
      G[1] = i;
      G[2] = j;
    }
  }

  public static void main(String[] args) {
    double[] Px = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double[] Py = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    int[] Ys = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    ClosestPair prog = new ClosestPair();
    double[] result = prog.find(0, Ys.length - 1, Px, Py, Ys);
    System.out.println(Arrays.toString(result));
  }
}