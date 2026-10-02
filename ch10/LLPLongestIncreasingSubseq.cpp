// LLP-LIS: G[j] >= G[i] + 1 for every i in pre(j) (i < j with A[i] < A[j]).

#include <iostream>
#include <vector>

std::vector<int> llpLIS(const std::vector<int>& A) {
    int n = (int)A.size();
    std::vector<std::vector<int>> pre(n);
    for (int j = 0; j < n; ++j)
        for (int i = 0; i < j; ++i)
            if (A[i] < A[j]) pre[j].push_back(i);
    std::vector<int> G(n, 1);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            int best = G[j];
            for (int i : pre[j]) if (G[i] + 1 > best) best = G[i] + 1;
            if (best > G[j]) { G[j] = best; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> A = {3, 10, 2, 1, 20, 4};
    auto G = llpLIS(A);
    int lis = 0;
    std::cout << "G =";
    for (int x : G) { std::cout << ' ' << x; if (x > lis) lis = x; }
    std::cout << ", LIS length = " << lis << '\n';
    return 0;
}
