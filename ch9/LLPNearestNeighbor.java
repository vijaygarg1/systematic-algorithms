// LLP-NearestNeighbor: find nearest neighbor distance for each point.
// px[j], py[j] are x,y coordinates of point j.

import java.util.*;

public class LLPNearestNeighbor {
  int n;
  double[] px;
  double[] py;
  double[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((G[j] > nearestDist(px, py, j)))) return false;
    return true;
  }

  private void advance() {
    G[j] = nearestDist(px, py, j);
  }

  public void LLPNearestNeighbor(double[] px, double[] py) {
    this.px = px;
    this.py = py;
    this.n = px.length;
    this.G = new double[n];
    for (int i = 0; i < n; i++) this.G[i] = Double.POSITIVE_INFINITY;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
  }

  public double nearestDist(double[] px, double[] py, int j) {
    double best = Integer.MAX_VALUE;
    int k = 0;
    while ((k < px.length)) {
      if ((k != j)) {
        double d = dist(px, py, j, k);
        if ((d < best)) {
          best = d;
        }
      }
      k = (k + 1);
    }
    return best;
  }

  public double dist(double[] px, double[] py, int j, int k) {
    double dx = (px[j] - px[k]);
    double dy = (py[j] - py[k]);
    return ((dx * dx) + (dy * dy));
  }

  public static void main(String[] args) {
    double[] px = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double[] py = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPNearestNeighbor prog = new LLPNearestNeighbor();
    prog.LLPNearestNeighbor(px, py);
    System.out.println(Arrays.toString(px));
    System.out.println(Arrays.toString(py));
  }
}