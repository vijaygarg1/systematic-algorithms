// LLP-ConvexHull: eliminate interior points to find convex hull membership.
// px[j], py[j] are x,y coordinates of point j.

import java.util.*;

public class LLPConvexHull {
  int n;
  double[] px;
  double[] py;
  boolean[] G;
  int j;

  private boolean forbidden(int j) {
    if (!(G[j])) return false;
    if (!(isInterior(j, px, py, G))) return false;
    return true;
  }

  private void advance() {
    G[j] = false;
  }

  public void LLPConvexHull(double[] px, double[] py) {
    this.px = px;
    this.py = py;
    this.n = px.length;
    this.G = new boolean[n];
    for (int i = 0; i < n; i++) this.G[i] = true;
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

  public boolean isInterior(int j, double[] px, double[] py, boolean[] G) {
    int i = 0;
    while ((i < px.length)) {
      if (((i != j) && G[i])) {
        int k = (i + 1);
        while ((k < px.length)) {
          if (((k != j) && G[k])) {
            int l = (k + 1);
            while ((l < px.length)) {
              if (((l != j) && G[l])) {
                if (insideTriangle(px, py, j, i, k, l)) {
                  return true;
                }
              }
              l = (l + 1);
            }
          }
          k = (k + 1);
        }
      }
      i = (i + 1);
    }
    return false;
  }

  public boolean insideTriangle(double[] px, double[] py, int j, int a, int b, int c) {
    double d1 = sign(px[j], py[j], px[a], py[a], px[b], py[b]);
    double d2 = sign(px[j], py[j], px[b], py[b], px[c], py[c]);
    double d3 = sign(px[j], py[j], px[c], py[c], px[a], py[a]);
    boolean hasNeg = (((d1 < 0.0) || (d2 < 0.0)) || (d3 < 0.0));
    boolean hasPos = (((d1 > 0.0) || (d2 > 0.0)) || (d3 > 0.0));
    return ((!hasNeg) || (!hasPos));
  }

  public double sign(double x1, double y1, double x2, double y2, double x3, double y3) {
    return (((x1 - x3) * (y2 - y3)) - ((x2 - x3) * (y1 - y3)));
  }

  public static void main(String[] args) {
    double[] px = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double[] py = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPConvexHull prog = new LLPConvexHull();
    prog.LLPConvexHull(px, py);
    System.out.println(Arrays.toString(px));
    System.out.println(Arrays.toString(py));
  }
}