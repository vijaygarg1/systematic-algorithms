// LLP-Traversal1: reachability on a directed graph.

#include <iostream>
#include <vector>

void llpTraversal(const std::vector<std::vector<int>>& pre,
                  std::vector<bool>& G) {
    int n = (int)G.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (G[j]) continue;
            for (int i : pre[j]) {
                if (G[i]) { G[j] = true; changed = true; break; }
            }
        }
    }
}

int main() {
    std::vector<std::vector<int>> pre = { {}, {0}, {0}, {1, 2}, {2}, {3, 4} };
    std::vector<bool> G(pre.size(), false);
    G[0] = true;
    llpTraversal(pre, G);
    std::cout << "reachable:";
    for (bool x : G) std::cout << ' ' << (x ? 1 : 0);
    std::cout << '\n';
    return 0;
}
