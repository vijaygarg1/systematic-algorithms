// LLP weighted interval scheduling: G[j] >= max(G[j-1], w[j] + G[p[j]]).

#include <iostream>
#include <vector>

int rhs(int j, const std::vector<int>& G,
        const std::vector<int>& w, const std::vector<int>& p) {
    int skip = G[j - 1];
    int take = w[j] + G[p[j]];
    return skip > take ? skip : take;
}

std::vector<int> llpWIS(const std::vector<int>& w,
                        const std::vector<int>& p) {
    int n = (int)w.size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 1; j < n; ++j) {
            int v = rhs(j, G, w, p);
            if (G[j] < v) { G[j] = v; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> w = {0, 4, 6, 5, 3, 7};
    std::vector<int> p = {0, 0, 0, 1, 3, 2};
    auto G = llpWIS(w, p);
    std::cout << "G =";
    for (int x : G) std::cout << ' ' << x;
    std::cout << "\noptimum = G[" << (int)G.size() - 1 << "] = " << G.back() << '\n';
    return 0;
}
