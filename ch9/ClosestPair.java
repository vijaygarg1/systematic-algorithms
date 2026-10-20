// Divide-and-conquer closest pair of points (squared distance), matching
// bxx-divideConquer.tex Algorithm ClosestPair: base case brute force for
// <= 3 points; otherwise recurse on both halves, then combine by scanning
// the strip of points within `best` of the dividing line, checking each
// strip point only against the next 15 points in y-order.
//
// PRECONDITION -- the book's first line ("Sort P by x-coordinate, presort
// once"), extended to BOTH coordinates so that the combine step never
// sorts anything:
// * Px[0..n-1] is non-decreasing, with Py[k] the y-coordinate of the
// same point as Px[k];
// * Ys[0..n-1] lists the same indices 0..n-1 in non-decreasing y-order.
// Both presorts are done once by the caller, with the host language's own
// sort (Arrays.sort, sorted(), std::sort, Array.prototype.sort), never
// inside the recursion.
//
// Every call receives its own range's indices in y-order, splits that
// order between the two halves in O(size) without re-sorting, and reads
// the strip off it already y-sorted. The combine step is therefore O(size)
// and T(n) = 2T(n/2) + O(n) = O(n log n) -- the classical refinement named
// at the end of the book's analysis, in place of the O(n log^2 n) version
// that re-sorted the strip at every level.
//
// Returns {best squared distance, index of one endpoint, index of the
// other}, so the result names the closest PAIR as the book's KwOut does,
// not just its separation. Distances stay squared throughout -- comparing
// d^2 orders points exactly as d does and avoids a sqrt.

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
    } else {
      if (((hi - lo) <= 2)) {
        int i = lo;
        while ((i <= (hi - 1))) {
          int j = (i + 1);
          while ((j <= hi)) {
            check(i, j, Px, Py, G);
            j = (j + 1);
          }
          i = (i + 1);
        }
      } else {
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
        int a = 0;
        while ((a < m)) {
          int b = (a + 1);
          int limit = (a + 15);
          while (((b < m) && (b <= limit))) {
            check(strip[a], strip[b], Px, Py, G);
            b = (b + 1);
          }
          a = (a + 1);
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