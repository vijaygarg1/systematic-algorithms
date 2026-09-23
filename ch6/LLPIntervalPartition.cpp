// LLP-IntervalPartition: assign each course j to the least free room
// not used by any overlapping earlier course in pre[j]; advance fixes
// j once all of its pre-set is fixed.

#include <iostream>
#include <vector>

std::vector<int> llpIntervalPartition(const std::vector<std::vector<int>>& pre) {
    int n = (int)pre.size();
    std::vector<int>  G(n, 1);
    std::vector<bool> fixed(n, false);

    auto leastFreeRoom = [&](int j) {
        int r = 1;
        for (bool conflict = true; conflict; ) {
            conflict = false;
            for (int i : pre[j]) if (G[i] == r) { conflict = true; break; }
            if (conflict) ++r;
        }
        return r;
    };

    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (fixed[j]) continue;
            bool ready = true;
            for (int i : pre[j]) if (!fixed[i]) { ready = false; break; }
            if (ready) { G[j] = leastFreeRoom(j); fixed[j] = true; changed = true; }
        }
    }
    return G;
}

int main() {
    // Three intervals: 1 conflicts with 0, 2 conflicts with both.
    std::vector<std::vector<int>> pre = { {}, {0}, {0, 1} };
    auto G = llpIntervalPartition(pre);
    std::cout << "rooms:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
