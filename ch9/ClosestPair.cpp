// Closest pair of points by divide-and-conquer (bxx-divideConquer.tex,
// algo:closest-pair). Returns {best squared distance, one endpoint, the
// other}, naming the closest PAIR as the book's KwOut does.
//
// PRECONDITION -- the book's "presort once" line, extended to BOTH
// coordinates so that the combine step never sorts: Px is non-decreasing
// with Py[k] the y-coordinate of the same point, and Ys lists the indices
// 0..n-1 in non-decreasing y-order. Both presorts are done once by the
// caller, with the host language's own std::sort.
//
// Every call receives its range's indices in y-order, splits that order
// between the halves in O(size), and reads the strip off it already
// y-sorted, so T(n) = 2T(n/2) + O(n) = O(n log n) -- the classical
// refinement named at the end of the book's analysis, in place of the
// O(n log^2 n) version that re-sorted the strip at every level.

#include <iostream>
#include <vector>
#include <limits>
#include <algorithm>
#include <cmath>

// Keep the pair (i, j) when it beats the best seen so far.
static void check(int i, int j,
                  const std::vector<double>& Px, const std::vector<double>& Py,
                  std::vector<double>& G) {
    double dx = Px[i] - Px[j], dy = Py[i] - Py[j];
    double d = dx * dx + dy * dy;
    if (d < G[0]) { G[0] = d; G[1] = i; G[2] = j; }
}

void closestPair(int lo, int hi,
                 const std::vector<double>& Px, const std::vector<double>& Py,
                 const std::vector<int>& Ys, std::vector<double>& G) {
    if (hi <= lo) return;
    if (hi - lo <= 2) {
        for (int i = lo; i < hi; ++i)
            for (int j = i + 1; j <= hi; ++j) check(i, j, Px, Py, G);
        return;
    }
    int mid = (lo + hi) / 2;
    double midX = Px[mid];
    // Divide: split the inherited y-order between the two halves, each
    // half keeping its points in y-order.
    std::vector<int> Ls, Rs;
    for (int p : Ys) (p <= mid ? Ls : Rs).push_back(p);
    closestPair(lo, mid, Px, Py, Ls, G);
    closestPair(mid + 1, hi, Px, Py, Rs, G);
    // Combine: the same y-order, filtered to the points within
    // sqrt(best) of the dividing line -- already sorted by y.
    std::vector<int> strip;
    for (int p : Ys) {
        double dx = Px[p] - midX;
        if (dx * dx < G[0]) strip.push_back(p);
    }
    // Each strip point need only be checked against the next 15 points
    // in y-order (Lemma: at most 15 positions apart).
    for (size_t a = 0; a < strip.size(); ++a)
        for (size_t b = a + 1; b < strip.size() && b <= a + 15; ++b)
            check(strip[a], strip[b], Px, Py, G);
}

std::vector<double> find(int lo, int hi,
                         const std::vector<double>& Px, const std::vector<double>& Py,
                         const std::vector<int>& Ys) {
    std::vector<double> G = {std::numeric_limits<double>::infinity(), -1, -1};
    closestPair(lo, hi, Px, Py, Ys, G);
    return G;
}

int main() {
    // The book's worked example (bxx-divideConquer.tex).
    std::vector<std::pair<double, double>> pts = {
        {1, 2}, {3, 7}, {5, 4}, {8, 9}, {9, 6}, {10, 2}};
    std::sort(pts.begin(), pts.end());                    // presort by x, once
    int n = (int)pts.size();
    std::vector<double> Px(n), Py(n);
    for (int i = 0; i < n; ++i) { Px[i] = pts[i].first; Py[i] = pts[i].second; }
    std::vector<int> Ys(n);
    for (int i = 0; i < n; ++i) Ys[i] = i;
    std::sort(Ys.begin(), Ys.end(),                       // presort by y, once
              [&](int a, int b) { return Py[a] < Py[b]; });
    auto G = find(0, n - 1, Px, Py, Ys);
    int i = (int)G[1], j = (int)G[2];
    std::cout << "closest pair: (" << Px[i] << ", " << Py[i] << ") and ("
              << Px[j] << ", " << Py[j] << "), squared distance " << G[0]
              << " (distance " << std::sqrt(G[0]) << ")\n";
    return 0;
}
