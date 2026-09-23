// LLP market clearing price: forbidden when item j is in a minimal
// overdemanded set; advance raises its price by 1.

#include <iostream>
#include <vector>

bool isOverDemanded(int j, const std::vector<std::vector<int>>& v,
                    const std::vector<int>& G) {
    int n = (int)G.size();
    int m = (int)v.size();
    int demandCount = 0;
    for (int b = 0; b < m; ++b) {
        int best = v[b][j] - G[j];
        bool isBest = true;
        for (int i = 0; i < n; ++i)
            if (v[b][i] - G[i] > best) { isBest = false; break; }
        if (isBest) ++demandCount;
    }
    return demandCount > 1;
}

std::vector<int> constrainedMarketClearingPrice(const std::vector<std::vector<int>>& v) {
    int n = (int)v[0].size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j)
            if (isOverDemanded(j, v, G)) { ++G[j]; changed = true; }
    }
    return G;
}

int main() {
    // 3 buyers, 3 items.
    std::vector<std::vector<int>> v = {
        {5, 3, 1},
        {4, 4, 2},
        {1, 2, 5}
    };
    auto G = constrainedMarketClearingPrice(v);
    std::cout << "prices:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
