// LLP form of Gale-Shapley stable matching.

#include <iostream>
#include <vector>

bool forbidden(int j, const std::vector<int>& G,
               const std::vector<std::vector<int>>& mpref,
               const std::vector<std::vector<int>>& rank) {
    int n = (int)G.size();
    for (int i = 0; i < n; ++i) {
        for (int k = 0; k <= G[i]; ++k) {
            int z = mpref[j][G[j]];
            if (mpref[j][G[j]] == mpref[i][k] &&
                rank[z][i] < rank[z][j]) return true;
        }
    }
    return false;
}

void advance(int j, std::vector<int>& G) { ++G[j]; }

std::vector<int> stableMarriage(const std::vector<std::vector<int>>& mpref,
                                const std::vector<std::vector<int>>& rank,
                                const std::vector<int>& I) {
    int n = (int)I.size();
    std::vector<int> G = I;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (forbidden(j, G, mpref, rank)) {
                advance(j, G);
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    // Three men, three women, 0-based.
    std::vector<std::vector<int>> mpref = {
        {0, 1, 2},
        {1, 0, 2},
        {0, 1, 2},
    };
    std::vector<std::vector<int>> rank = {
        {2, 1, 3},
        {1, 2, 3},
        {1, 2, 3},
    };
    std::vector<int> I = {0, 0, 0};
    auto G = stableMarriage(mpref, rank, I);
    for (int j = 0; j < (int)G.size(); ++j) {
        std::cout << "man " << j << ": index " << G[j]
                  << ", matched with woman " << mpref[j][G[j]] << '\n';
    }
    return 0;
}
