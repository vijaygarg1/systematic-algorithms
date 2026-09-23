// LLP FPTAS for Knapsack: scaled-value DP on the 2D lattice G[i, c]
// driven by a forbidden / advance pair on (i, c) pairs.

#include <iostream>
#include <vector>

std::vector<std::vector<int>> llpApproxKnapsack(const std::vector<int>& w,
                                                const std::vector<int>& v,
                                                int W, int epsNum, int epsDen) {
    int n = (int)w.size();
    int M = v[0];
    for (int i = 1; i < n; ++i) if (v[i] > M) M = v[i];

    std::vector<int> vPrime(n);
    for (int i = 0; i < n; ++i)
        vPrime[i] = (long long)v[i] * n * epsDen / ((long long)epsNum * M);

    std::vector<std::vector<int>> G(n + 1, std::vector<int>(W + 1, 0));
    bool changed = true;
    while (changed) {
        changed = false;
        for (int i = 1; i <= n; ++i) {
            for (int c = 0; c <= W; ++c) {
                int target = G[i - 1][c];
                if (w[i - 1] <= c) {
                    int take = G[i - 1][c - w[i - 1]] + vPrime[i - 1];
                    if (take > target) target = take;
                }
                if (G[i][c] < target) { G[i][c] = target; changed = true; }
            }
        }
    }
    return G;
}

int main() {
    std::vector<int> w = {2, 3, 4};
    std::vector<int> v = {30, 40, 50};
    int W = 6;
    auto G = llpApproxKnapsack(w, v, W, /*epsNum=*/1, /*epsDen=*/5);
    std::cout << "G[n][W] (scaled) = " << G.back().back() << '\n';
    return 0;
}
