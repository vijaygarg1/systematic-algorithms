// Closest pair of points by divide-and-conquer (bxx-divideConquer.tex,
// algo:closest-pair). Px sorted by x. Returns the smallest squared
// distance (G[0]). Combine step: gather the strip of points within
// `best` of the dividing line, sort the strip BY Y-COORDINATE, and
// check each strip point only against the next 15 points in that
// y-order -- the book's O(n log^2 n) bound, not an O((hi-lo)^2)
// brute-force scan over every pair in range.

#include <iostream>
#include <vector>
#include <limits>
#include <algorithm>

void closestPair(int lo, int hi,
                 const std::vector<double>& Px,
                 const std::vector<double>& Py,
                 std::vector<double>& G) {
    if (hi <= lo) return;
    if (hi - lo <= 2) {
        for (int i = lo; i < hi; ++i)
            for (int j = i + 1; j <= hi; ++j) {
                double dx = Px[i] - Px[j], dy = Py[i] - Py[j];
                double d = dx * dx + dy * dy;
                if (d < G[0]) G[0] = d;
            }
        return;
    }
    int mid = (lo + hi) / 2;
    double midX = Px[mid];
    closestPair(lo, mid, Px, Py, G);
    closestPair(mid + 1, hi, Px, Py, G);
    std::vector<int> strip;
    for (int k = lo; k <= hi; ++k) {
        double dx = Px[k] - midX;
        if (dx * dx < G[0]) strip.push_back(k);
    }
    std::sort(strip.begin(), strip.end(),
              [&](int a, int b) { return Py[a] < Py[b]; });
    for (size_t a = 0; a < strip.size(); ++a) {
        for (size_t b = a + 1; b < strip.size() && b <= a + 15; ++b) {
            int pi = strip[a], pj = strip[b];
            double dx = Px[pi] - Px[pj], dy = Py[pi] - Py[pj];
            double d = dx * dx + dy * dy;
            if (d < G[0]) G[0] = d;
        }
    }
}

std::vector<double> find(int lo, int hi,
                         const std::vector<double>& Px,
                         const std::vector<double>& Py) {
    std::vector<double> G = {std::numeric_limits<double>::infinity()};
    closestPair(lo, hi, Px, Py, G);
    return G;
}

int main() {
    std::vector<double> Px = {0.0, 1.0, 3.0, 4.0, 7.0};
    std::vector<double> Py = {0.0, 5.0, 2.0, 1.0, 6.0};
    auto G = find(0, (int)Px.size() - 1, Px, Py);
    std::cout << "min squared distance: " << G[0] << '\n';
    return 0;
}
