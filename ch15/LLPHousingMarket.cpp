// LLP housing market: forbidden when agent j is not in S(G) (the
// largest submatching) but wishes for a house held by an agent who is;
// advance moves j to its next preference. S(G) is the set of agents
// lying on a cycle of the wish functional graph (i -> wish(i)).

#include <iostream>
#include <vector>

int wish(int i, const std::vector<int>& G,
        const std::vector<std::vector<int>>& pref) {
    return pref[i][G[i]];
}

bool inSubmatching(int j, const std::vector<int>& G,
                   const std::vector<std::vector<int>>& pref) {
    int n = (int)G.size();
    int cur = wish(j, G, pref);
    int steps = 1;
    while (steps <= n) {
        if (cur == j) return true;
        cur = wish(cur, G, pref);
        ++steps;
    }
    return false;
}

std::vector<int> llpHousingMarket(const std::vector<std::vector<int>>& pref) {
    int n = (int)pref.size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (!inSubmatching(j, G, pref) && inSubmatching(wish(j, G, pref), G, pref)) {
                ++G[j];
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> pref = {
        {1, 2, 0, 3},
        {0, 3, 1, 2},
        {0, 1, 3, 2},
        {1, 0, 2, 3}
    };
    auto G = llpHousingMarket(pref);
    std::cout << "G:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
