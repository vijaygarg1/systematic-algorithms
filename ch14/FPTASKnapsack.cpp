// FPTAS for Knapsack: scale values down by scale = eps*M/n, then run the
// standard 0/1 DP on the scaled instance.

#include <iostream>
#include <vector>

std::vector<bool> fptasKnapsack(const std::vector<int>& w,
                                const std::vector<int>& v,
                                int W, int epsNum, int epsDen) {
    int n = (int)w.size();
    int M = v[0];
    for (int i = 1; i < n; ++i) if (v[i] > M) M = v[i];

    std::vector<int> vPrime(n);
    for (int i = 0; i < n; ++i)
        vPrime[i] = (long long)v[i] * n * epsDen / ((long long)epsNum * M);

    std::vector<std::vector<int>> dp(n + 1, std::vector<int>(W + 1, 0));
    for (int i = 1; i <= n; ++i)
        for (int c = 0; c <= W; ++c) {
            dp[i][c] = dp[i - 1][c];
            if (w[i - 1] <= c) {
                int take = dp[i - 1][c - w[i - 1]] + vPrime[i - 1];
                if (take > dp[i][c]) dp[i][c] = take;
            }
        }
    std::vector<bool> S(n, false);
    int rem = W;
    for (int i = n; i > 0; --i)
        if (dp[i][rem] != dp[i - 1][rem]) { S[i - 1] = true; rem -= w[i - 1]; }
    return S;
}

int main() {
    std::vector<int> w = {2, 3, 4};
    std::vector<int> v = {30, 40, 50};
    int W = 6;
    auto S = fptasKnapsack(w, v, W, /*epsNum=*/1, /*epsDen=*/5);  // eps = 1/5
    std::cout << "picked:";
    for (size_t i = 0; i < S.size(); ++i) if (S[i]) std::cout << ' ' << i;
    std::cout << '\n';
    return 0;
}
