// Classical divide-and-conquer planar convex hull.
// Split by median x-coordinate, recursively hull each half, merge the two
// sub-hulls via their upper and lower common tangents.
#include <vector>
#include <algorithm>
#include <iostream>
using namespace std;

struct Pt { double x, y; };
bool operator<(const Pt& a, const Pt& b) { return a.x != b.x ? a.x < b.x : a.y < b.y; }

double cross(const Pt& o, const Pt& a, const Pt& b) {
  return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
}

// Convex hull of a small point set via Andrew's monotone chain. Used both
// as the recursion's base case and, on the union of two sub-hulls'
// vertices, as the merge step.
vector<Pt> hullOf(vector<Pt> pts) {
  sort(pts.begin(), pts.end());
  pts.erase(unique(pts.begin(), pts.end(), [](const Pt& a, const Pt& b) { return a.x == b.x && a.y == b.y; }), pts.end());
  int n = pts.size();
  if (n <= 2) return pts;
  vector<Pt> lower, upper;
  for (auto& p : pts) {
    while (lower.size() >= 2 && cross(lower[lower.size() - 2], lower.back(), p) <= 0) lower.pop_back();
    lower.push_back(p);
  }
  for (int i = n - 1; i >= 0; i--) {
    while (upper.size() >= 2 && cross(upper[upper.size() - 2], upper.back(), pts[i]) <= 0) upper.pop_back();
    upper.push_back(pts[i]);
  }
  lower.pop_back();
  upper.pop_back();
  lower.insert(lower.end(), upper.begin(), upper.end());
  return lower;
}

vector<Pt> convexHull(vector<Pt> pts) {
  sort(pts.begin(), pts.end());
  pts.erase(unique(pts.begin(), pts.end(), [](const Pt& a, const Pt& b) { return a.x == b.x && a.y == b.y; }), pts.end());
  if (pts.size() <= 3) return hullOf(pts);
  int mid = (pts.size() + 1) / 2;
  vector<Pt> left(pts.begin(), pts.begin() + mid);
  vector<Pt> right(pts.begin() + mid, pts.end());
  vector<Pt> leftHull = convexHull(left);
  vector<Pt> rightHull = convexHull(right);
  vector<Pt> combined = leftHull;
  combined.insert(combined.end(), rightHull.begin(), rightHull.end());
  return hullOf(combined);
}

int main() {
  vector<Pt> points = {{0,0},{2,2},{2.3,3},{4,0.5},{3,-1},{1,-2},{-1,-1}};
  for (auto& p : convexHull(points)) cout << "(" << p.x << "," << p.y << ")\n";
  return 0;
}
