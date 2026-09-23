// Connected components by ensure-clause label propagation.
//   ensure(j) : G[j] >= max { G[i] | i in adj[j] }

#include <iostream>
#include <vector>

std::vector<int> slowComponents(const std::vector<std::vector<int>>& adj) {
    int n = (int)adj.size();
    std::vector<int> G(n);
    for (int j = 0; j < n; ++j) G[j] = j;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (adj[j].empty()) continue;
            int m = G[adj[j][0]];
            for (int i : adj[j]) if (G[i] > m) m = G[i];
            if (G[j] < m) { G[j] = m; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> adj = {
        {1, 2}, {0, 2}, {0, 1}, {4}, {3}
    };
    auto G = slowComponents(adj);
    std::cout << "labels:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
