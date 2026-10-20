// Classical O(n^2) LIS DP.

#include <iostream>
#include <vector>

std::vector<int> solve(const std::vector<int>& A) {
    int n = (int)A.size();
    std::vector<int> dp(n, 1);
    for (int i = 1; i < n; ++i) {
        for (int j = 0; j < i; ++j) {
            if (A[j] < A[i] && dp[j] + 1 > dp[i]) dp[i] = dp[j] + 1;
        }
    }
    return dp;
}

int main() {
    std::vector<int> A = {3, 10, 2, 1, 20, 4};
    auto dp = solve(A);
    int lis = 0;
    std::cout << "A =";
    for (int x : A) std::cout << ' ' << x;
    std::cout << "\ndp =";
    for (int x : dp) { std::cout << ' ' << x; if (x > lis) lis = x; }
    std::cout << ", LIS length = " << lis << '\n';
    return 0;
}
