// LLP-Layering: each j advances once all predecessors are fixed.

#include <iostream>
#include <vector>

std::vector<int> llpLayering(const std::vector<std::vector<int>>& pre) {
    int n = (int)pre.size();
    std::vector<int>  G(n, 0);
    std::vector<bool> fixedFlag(n, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (fixedFlag[j]) continue;
            bool allFixed = true;
            for (int i : pre[j]) if (!fixedFlag[i]) { allFixed = false; break; }
            if (!allFixed) continue;
            int best = 0;
            for (int i : pre[j]) if (G[i] + 1 > best) best = G[i] + 1;
            G[j] = best;
            fixedFlag[j] = true;
            changed = true;
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> pre = {{}, {0}, {0}, {1, 2}, {2}, {3, 4}};
    auto G = llpLayering(pre);
    std::cout << "LLP layers:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
