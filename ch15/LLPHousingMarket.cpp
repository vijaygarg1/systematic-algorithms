// LLP housing market: forbidden when agent j is not in the
// submatching but wishes for a house that is in the submatching;
// advance increments the proposal index.

#include <iostream>
#include <vector>

bool inSubmatching(int j, const std::vector<int>& G,
                   const std::vector<std::vector<int>>& pref) {
    int n = (int)G.size();
    int target = pref[j][G[j]];
    for (int i = 0; i < n; ++i)
        if (i != j && pref[i][G[i]] == target) return false;
    return true;
}

bool wishInSubmatching(int j, const std::vector<int>& G,
                       const std::vector<std::vector<int>>& pref) {
    int n = (int)G.size();
    int wish = pref[j][G[j]];
    for (int i = 0; i < n; ++i)
        if (pref[i][G[i]] == wish && inSubmatching(i, G, pref)) return true;
    return false;
}

std::vector<int> llpHousingMarket(const std::vector<std::vector<int>>& pref) {
    int n = (int)pref.size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (!inSubmatching(j, G, pref) && wishInSubmatching(j, G, pref)) {
                ++G[j];
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> pref = {
        {1, 0, 2, 3},
        {0, 1, 2, 3},
        {0, 1, 2, 3},
        {3, 1, 0, 2}
    };
    auto G = llpHousingMarket(pref);
    std::cout << "G:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
