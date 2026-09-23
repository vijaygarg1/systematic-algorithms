// Classical divide-and-conquer planar convex hull.
// Split by median x-coordinate, recursively hull each half, merge the two
// sub-hulls via their upper and lower common tangents.

import java.util.*;

public class ConvexHull {
  static class Pt {
    double x, y;
    Pt(double x, double y) { this.x = x; this.y = y; }
    public String toString() { return "(" + x + "," + y + ")"; }
  }

  static double cross(Pt o, Pt a, Pt b) {
    return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
  }

  // Convex hull of a small point set via Andrew's monotone chain. Used both
  // as the recursion's base case and, on the union of two sub-hulls'
  // vertices, as the merge step (a point interior to either sub-hull is
  // interior to the combined hull too, so only the sub-hulls' own vertices
  // can appear in the merged hull).
  static List<Pt> hullOf(List<Pt> input) {
    List<Pt> pts = new ArrayList<>(new LinkedHashSet<>(input));
    pts.sort((p, q) -> p.x != q.x ? Double.compare(p.x, q.x) : Double.compare(p.y, q.y));
    if (pts.size() <= 2) return pts;
    List<Pt> lower = new ArrayList<>();
    for (Pt p : pts) {
      while (lower.size() >= 2 && cross(lower.get(lower.size() - 2), lower.get(lower.size() - 1), p) <= 0)
        lower.remove(lower.size() - 1);
      lower.add(p);
    }
    List<Pt> upper = new ArrayList<>();
    for (int i = pts.size() - 1; i >= 0; i--) {
      Pt p = pts.get(i);
      while (upper.size() >= 2 && cross(upper.get(upper.size() - 2), upper.get(upper.size() - 1), p) <= 0)
        upper.remove(upper.size() - 1);
      upper.add(p);
    }
    lower.remove(lower.size() - 1);
    upper.remove(upper.size() - 1);
    lower.addAll(upper);
    return lower;
  }

  static List<Pt> convexHull(List<Pt> input) {
    List<Pt> pts = new ArrayList<>(new LinkedHashSet<>(input));
    pts.sort((p, q) -> p.x != q.x ? Double.compare(p.x, q.x) : Double.compare(p.y, q.y));
    if (pts.size() <= 3) return hullOf(pts);
    int mid = (pts.size() + 1) / 2;
    List<Pt> leftHull = convexHull(pts.subList(0, mid));
    List<Pt> rightHull = convexHull(pts.subList(mid, pts.size()));
    List<Pt> combined = new ArrayList<>(leftHull);
    combined.addAll(rightHull);
    return hullOf(combined);
  }

  public static void main(String[] args) {
    List<Pt> points = Arrays.asList(
      new Pt(0, 0), new Pt(2, 2), new Pt(2.3, 3), new Pt(4, 0.5),
      new Pt(3, -1), new Pt(1, -2), new Pt(-1, -1));
    for (Pt p : convexHull(points)) System.out.println(p);
  }
}
