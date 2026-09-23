// LLP-Antichain: maximum antichain by advancing chain indices to
// dominate-free positions.

#include <iostream>
#include <vector>

std::vector<int> llpAntichain(const std::vector<std::vector<int>>& chains,
                              const std::vector<int>& len,
                              const std::vector<std::vector<bool>>& leq) {
    int n = (int)len.size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (G[j] >= len[j]) continue;
            bool dominated = false;
            for (int k = 0; k < n && !dominated; ++k)
                if (k != j && leq[chains[j][G[j]]][chains[k][G[k]]])
                    dominated = true;
            if (dominated) { ++G[j]; changed = true; }
        }
    }
    return G;
}

int main() {
    // Two two-element chains over a 4-element poset with no cross-relations.
    std::vector<std::vector<int>> chains = { {0, 1}, {2, 3} };
    std::vector<int> len = {2, 2};
    std::vector<std::vector<bool>> leq(4, std::vector<bool>(4, false));
    for (int i = 0; i < 4; ++i) leq[i][i] = true;
    leq[0][1] = true;
    leq[2][3] = true;
    auto G = llpAntichain(chains, len, leq);
    std::cout << "G:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
