// LLP-Knapsack: forbidden(i,c) over the full (i,c) lattice, matching
// bxx-dynamicProgramming.tex Algorithm LLP-Knapsack.

#include <iostream>
#include <vector>

std::vector<std::vector<int>> llpKnapsack(const std::vector<int>& w, const std::vector<int>& v, int W) {
    int n = (int)w.size() - 1;
    std::vector<std::vector<int>> G(n + 1, std::vector<int>(W + 1, 0));
    bool changed = true;
    while (changed) {
        changed = false;
        for (int i = 1; i <= n; ++i) {
            for (int c = 1; c <= W; ++c) {
                if (G[i][c] < G[i - 1][c]) {
                    G[i][c] = G[i - 1][c];
                    changed = true;
                }
                if (w[i] <= c && G[i][c] < G[i - 1][c - w[i]] + v[i]) {
                    G[i][c] = G[i - 1][c - w[i]] + v[i];
                    changed = true;
                }
            }
        }
    }
    return G;
}

int main() {
    std::vector<int> w = {0, 2, 3, 4, 5};
    std::vector<int> v = {0, 3, 4, 5, 6};
    int W = 8;
    auto G = llpKnapsack(w, v, W);
    int n = (int)w.size() - 1;
    std::cout << "optimum = G[" << n << "][" << W << "] = " << G[n][W] << '\n';
    return 0;
}
