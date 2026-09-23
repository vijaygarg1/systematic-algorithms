// Gale's Top Trading Cycle (TTC) algorithm for the housing market.

#include <iostream>
#include <vector>

std::vector<int> ttc(const std::vector<std::vector<int>>& pref) {
    int n = (int)pref.size();
    std::vector<int>  house(n, -1), G(n, 0);
    std::vector<bool> fixed(n, false), onPath(n, false);
    int numFixed = 0;
    while (numFixed < n) {
        // Each unfixed agent points to its first remaining unfixed top choice.
        for (int i = 0; i < n; ++i)
            if (!fixed[i])
                while (fixed[pref[i][G[i]]]) ++G[i];
        // Find a cycle starting from any unfixed agent.
        std::fill(onPath.begin(), onPath.end(), false);
        int start = 0;
        while (fixed[start]) ++start;
        int cur = start;
        onPath[cur] = true;
        int nxt = pref[cur][G[cur]];
        while (!onPath[nxt]) {
            cur = nxt;
            onPath[cur] = true;
            nxt = pref[cur][G[cur]];
        }
        // Walk the cycle, assigning each agent his current wish.
        int cycleStart = nxt;
        cur = cycleStart;
        while (true) {
            int wish = pref[cur][G[cur]];
            house[cur] = wish;
            fixed[cur] = true;
            ++numFixed;
            if (wish == cycleStart) break;
            cur = wish;
        }
    }
    return house;
}

int main() {
    std::vector<std::vector<int>> pref = {
        {1, 0, 2, 3},
        {0, 1, 2, 3},
        {0, 1, 2, 3},
        {3, 1, 0, 2}
    };
    auto h = ttc(pref);
    std::cout << "house:";
    for (int x : h) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
