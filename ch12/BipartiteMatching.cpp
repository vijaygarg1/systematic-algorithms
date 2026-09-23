// Classical augmenting-path bipartite matching.

#include <iostream>
#include <vector>

bool tryMatch(int u, const std::vector<std::vector<int>>& adj,
              std::vector<int>& partner, std::vector<bool>& seen) {
    int m = (int)adj[0].size();
    for (int v = 0; v < m; ++v) {
        if (adj[u][v] == 1 && !seen[v]) {
            seen[v] = true;
            if (partner[v] == -1 || tryMatch(partner[v], adj, partner, seen)) {
                partner[v] = u;
                return true;
            }
        }
    }
    return false;
}

std::vector<int> bipartiteMatching(const std::vector<std::vector<int>>& adj) {
    int n = (int)adj.size();
    int m = (int)adj[0].size();
    std::vector<int> G(n, 0), partner(m, -1);
    for (int u = 0; u < n; ++u) {
        std::vector<bool> seen(m, false);
        if (tryMatch(u, adj, partner, seen)) G[u] = 1;
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> adj = {
        {1, 1, 0},
        {1, 0, 0},
        {0, 0, 1}
    };
    auto G = bipartiteMatching(adj);
    std::cout << "matched on left:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
