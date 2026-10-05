// Divide-and-conquer closest-pair-of-points (squared distance), matching
// bxx-divideConquer.tex Algorithm ClosestPair: base case brute force for
// <=3 points; otherwise recurse on both halves, then combine by
// collecting the strip of points within `best` of the dividing line,
// sorting the strip BY Y-COORDINATE, and checking each strip point only
// against the next 15 points in that y-order -- giving the book's
// O(n log^2 n) bound (re-sorting the strip at every level), not an
// O((hi-lo)^2) brute-force scan over every pair in range.

import java.util.*;

public class ClosestPair {
  public double[] find(int lo, int hi, double[] Px, double[] Py) {
    double[] G = new double[1];
    G[0] = Double.POSITIVE_INFINITY;
    ClosestPair(lo, hi, Px, Py, G);
    return G;
  }

  public void ClosestPair(int lo, int hi, double[] Px, double[] Py, double[] G) {
    if ((hi <= lo)) {
    } else {
      if (((hi - lo) <= 2)) {
        int i = lo;
        while ((i <= (hi - 1))) {
          int j = (i + 1);
          while ((j <= hi)) {
            double d = (sq((Px[i] - Px[j])) + sq((Py[i] - Py[j])));
            if ((d < G[0])) {
              G[0] = d;
            }
            j = (j + 1);
          }
          i = (i + 1);
        }
      } else {
        int mid = ((lo + hi) / 2);
        double midX = Px[mid];
        ClosestPair(lo, mid, Px, Py, G);
        ClosestPair((mid + 1), hi, Px, Py, G);
        int n = ((hi - lo) + 1);
        int[] strip = new int[n];
        int m = 0;
        int k = lo;
        while ((k <= hi)) {
          double dx = (Px[k] - midX);
          if (((dx * dx) < G[0])) {
            strip[m] = k;
            m = (m + 1);
          }
          k = (k + 1);
        }
        mergeSortByY(strip, 0, (m - 1), Py);
        int a = 0;
        while ((a < m)) {
          int b = (a + 1);
          int limit = (a + 15);
          while (((b < m) && (b <= limit))) {
            int pi = strip[a];
            int pj = strip[b];
            double d = (sq((Px[pi] - Px[pj])) + sq((Py[pi] - Py[pj])));
            if ((d < G[0])) {
              G[0] = d;
            }
            b = (b + 1);
          }
          a = (a + 1);
        }
      }
    }
  }

  public double sq(double x) {
    return (x * x);
  }

  public void mergeSortByY(int[] idx, int lo, int hi, double[] Py) {
    if ((lo < hi)) {
      int mid = ((lo + hi) / 2);
      mergeSortByY(idx, lo, mid, Py);
      mergeSortByY(idx, (mid + 1), hi, Py);
      merge(idx, lo, mid, hi, Py);
    }
  }

  public void merge(int[] idx, int lo, int mid, int hi, double[] Py) {
    int n = ((hi - lo) + 1);
    int[] tmp = new int[n];
    int i = lo;
    int j = (mid + 1);
    int t = 0;
    while (((i <= mid) && (j <= hi))) {
      if ((Py[idx[i]] <= Py[idx[j]])) {
        tmp[t] = idx[i];
        i = (i + 1);
      } else {
        tmp[t] = idx[j];
        j = (j + 1);
      }
      t = (t + 1);
    }
    while ((i <= mid)) {
      tmp[t] = idx[i];
      i = (i + 1);
      t = (t + 1);
    }
    while ((j <= hi)) {
      tmp[t] = idx[j];
      j = (j + 1);
      t = (t + 1);
    }
    int p = 0;
    while ((p < n)) {
      idx[(lo + p)] = tmp[p];
      p = (p + 1);
    }
  }

  public static void main(String[] args) {
    int hi = 0;
    double[] Px = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double[] Py = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    ClosestPair prog = new ClosestPair();
    double[] result = prog.find(0, hi, Px, Py);
    System.out.println(Arrays.toString(result));
  }
}