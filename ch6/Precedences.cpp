// Precedences: composition program enforcing G[j] >= G[i] + t[i] for
// every i in pre(j).

#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

std::vector<int> precedences(const std::vector<std::set<int>>& pre,
                             const std::vector<int>& t,
                             std::vector<int> G) {
    int n = (int)G.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            int target = G[j];
            for (int i : pre[j]) target = std::max(target, G[i] + t[i]);
            if (target > G[j]) { G[j] = target; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<std::set<int>> pre = {{}, {0}, {0, 1}};
    std::vector<int> t = {3, 4, 2};
    std::vector<int> G = {0, 0, 0};
    auto out = precedences(pre, t, G);
    std::cout << "G:";
    for (int g : out) std::cout << ' ' << g;
    std::cout << '\n';
    return 0;
}
