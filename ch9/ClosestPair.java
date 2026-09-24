// Divide-and-conquer closest-pair-of-points (squared distance).

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
        for (int i = lo; i < hi; i++) {
          for (int j = (i + 1); j <= hi; j++) {
            if (((((Px[i] - Px[j]) * (Px[i] - Px[j])) + ((Py[i] - Py[j]) * (Py[i] - Py[j]))) < G[0])) {
              G[0] = (((Px[i] - Px[j]) * (Px[i] - Px[j])) + ((Py[i] - Py[j]) * (Py[i] - Py[j])));
            }
          }
        }
      } else {
        int mid = ((lo + hi) / 2);
        /* [ ... [] ... ]: parallel branches (sequential for now) */
        // branch 0
        ClosestPair(lo, mid, Px, Py, G);
        // branch 1
        ClosestPair((mid + 1), hi, Px, Py, G);
        for (int i = lo; i < hi; i++) {
          for (int j = (i + 1); j <= hi; j++) {
            if ((((((Px[i] - Px[mid]) * (Px[i] - Px[mid])) < G[0]) && (((Px[j] - Px[mid]) * (Px[j] - Px[mid])) < G[0])) && ((((Px[i] - Px[j]) * (Px[i] - Px[j])) + ((Py[i] - Py[j]) * (Py[i] - Py[j]))) < G[0]))) {
              G[0] = (((Px[i] - Px[j]) * (Px[i] - Px[j])) + ((Py[i] - Py[j]) * (Py[i] - Py[j])));
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int hi = 0;
    double[] Px = new double[] {1.0, 2.0, 3.0, 4.0};
    double[] Py = new double[] {1.0, 2.0, 3.0, 4.0};
    ClosestPair prog = new ClosestPair();
    double[] result = prog.find(0, hi, Px, Py);
    System.out.println(Arrays.toString(result));
  }
}