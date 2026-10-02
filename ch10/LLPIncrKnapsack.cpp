// LLP-IncrKnapsack: add a single item (w, v) on top of previous row C.

#include <iostream>
#include <vector>

std::vector<int> llpIncrKnapsack(int w, int v, const std::vector<int>& C) {
    int n = (int)C.size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int c = 0; c < n; ++c) {
            int skip = C[c];
            int take = (c >= w) ? C[c - w] + v : -1;
            int best = skip > take ? skip : take;
            if (G[c] < best) { G[c] = best; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> C = {0, 0, 3, 3, 3, 3, 3, 3, 3};
    auto G = llpIncrKnapsack(3, 4, C);
    std::cout << "new row G =";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
