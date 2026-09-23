// Closest pair of points by divide-and-conquer.  Px sorted by x.
// Returns the smallest squared distance (G[0]).

#include <iostream>
#include <vector>
#include <limits>

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
    closestPair(lo, mid, Px, Py, G);
    closestPair(mid + 1, hi, Px, Py, G);
    for (int i = lo; i < hi; ++i)
        for (int j = i + 1; j <= hi; ++j) {
            double a = Px[i] - Px[mid], b = Px[j] - Px[mid];
            if (a * a < G[0] && b * b < G[0]) {
                double dx = Px[i] - Px[j], dy = Py[i] - Py[j];
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
