// LLP-NearestNeighbor: G[j] is the squared distance from point j to its
// nearest other point. Each advance computes an O(n) minimum and each j
// advances at most once, so the total work is O(n^2) -- the brute-force
// bound, which is what the book's LLP formulation trades for simplicity.

#include <iostream>
#include <vector>
#include <limits>

static double dist(const std::vector<double>& px, const std::vector<double>& py,
                   int j, int k) {
    double dx = px[j] - px[k];
    double dy = py[j] - py[k];
    return dx * dx + dy * dy;
}

static double nearestDist(const std::vector<double>& px,
                          const std::vector<double>& py, int j) {
    double best = std::numeric_limits<double>::infinity();
    for (int k = 0; k < (int) px.size(); ++k) {
        if (k == j) continue;
        double d = dist(px, py, j, k);
        if (d < best) best = d;
    }
    return best;
}

std::vector<double> llpNearestNeighbor(const std::vector<double>& px,
                                       const std::vector<double>& py) {
    int n = (int) px.size();
    std::vector<double> G(n, std::numeric_limits<double>::infinity());
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            double d = nearestDist(px, py, j);
            if (G[j] > d) { G[j] = d; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<double> px = {0, 3, 1}, py = {0, 4, 1};
    std::vector<double> G = llpNearestNeighbor(px, py);
    std::cout << "squared nearest distances:";
    for (double x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
