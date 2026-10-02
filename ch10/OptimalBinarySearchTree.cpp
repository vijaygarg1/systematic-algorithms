// Optimal BST: O(n^3) DP over interval [i, j] picking the root r.

#include <iostream>
#include <vector>
#include <limits>

std::vector<std::vector<double>> solve(const std::vector<double>& p) {
    int n = (int)p.size();
    std::vector<std::vector<double>> dp(n, std::vector<double>(n, 0.0));
    std::vector<std::vector<double>> s (n, std::vector<double>(n, 0.0));
    for (int i = 0; i < n; ++i) { dp[i][i] = p[i]; s[i][i] = p[i]; }
    for (int length = 1; length < n; ++length) {
        for (int lo = 0; lo + length < n; ++lo) {
            int hi = lo + length;
            s[lo][hi] = s[lo][hi - 1] + p[hi];
            double best = std::numeric_limits<double>::infinity();
            for (int r = lo; r <= hi; ++r) {
                double left  = (r > lo) ? dp[lo][r - 1] : 0.0;
                double right = (r < hi) ? dp[r + 1][hi] : 0.0;
                double cost = s[lo][hi] + left + right;
                if (cost < best) best = cost;
            }
            dp[lo][hi] = best;
        }
    }
    return dp;
}

int main() {
    std::vector<double> p = {0.25, 0.20, 0.30, 0.25};
    auto dp = solve(p);
    std::cout << "optimum BST cost = " << dp[0][p.size() - 1] << '\n';
    return 0;
}
