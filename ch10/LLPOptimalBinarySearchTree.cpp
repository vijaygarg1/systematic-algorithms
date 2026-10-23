// LLP-OptimalBinarySearchTree: G[i][j] is the optimal expected cost for
// the key range [i..j]. Sub-ranges are settled shortest-first (priority
// j - i), so the smaller ranges a cost depends on are already final.

#include <iostream>
#include <vector>
#include <limits>
#include <algorithm>

static double optCost(const std::vector<double>& p,
                      const std::vector<std::vector<double>>& G,
                      int i, int j) {
    double best = std::numeric_limits<double>::infinity();
    // Loop-invariant in k: computed once, not once per root.
    double s = 0.0;
    for (int l = i; l <= j; ++l) s += p[l];
    for (int k = i; k <= j; ++k) {
        double leftCost = (k > i) ? G[i][k - 1] : 0.0;
        double rightCost = (k < j) ? G[k + 1][j] : 0.0;
        best = std::min(best, leftCost + s + rightCost);
    }
    return best;
}

void llpOptimalBinarySearchTree(const std::vector<double>& p,
                                std::vector<std::vector<double>>& G) {
    int n = (int) p.size();
    std::vector<std::pair<int, int>> order;
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
            if (i <= j) order.push_back({i, j});
    std::stable_sort(order.begin(), order.end(),
                     [](const std::pair<int,int>& a, const std::pair<int,int>& b) {
                         return (a.second - a.first) < (b.second - b.first);
                     });
    bool changed = true;
    while (changed) {
        changed = false;
        for (size_t x = 0; x < order.size(); ++x) {
            int i = order[x].first, j = order[x].second;
            double c = optCost(p, G, i, j);
            if (G[i][j] < c) { G[i][j] = c; changed = true; }
        }
    }
}

int main() {
    std::vector<double> p = {0.1, 0.2, 0.4};
    int n = (int) p.size();
    std::vector<std::vector<double>> G(n, std::vector<double>(n, 0.0));
    llpOptimalBinarySearchTree(p, G);
    std::cout << "optimal cost over all keys = " << G[0][n - 1] << '\n';
    return 0;
}
