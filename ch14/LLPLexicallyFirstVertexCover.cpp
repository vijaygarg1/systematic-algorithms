// LLP parallel 2-approximation for Vertex Cover: pick every endpoint
// of a lex-minimal uncovered edge.

#include <iostream>
#include <vector>

bool lexLess(int a, int b, int c, int d) {
    int amin = std::min(a, b), amax = std::max(a, b);
    int cmin = std::min(c, d), cmax = std::max(c, d);
    if (amin < cmin) return true;
    if (amin > cmin) return false;
    return amax < cmax;
}

bool isLexMinIncident(int i, int j,
                      const std::vector<std::vector<int>>& adj,
                      const std::vector<bool>& G) {
    if (adj[i][j] != 1 || G[i] || G[j]) return false;
    int n = (int)G.size();
    for (int x = 0; x < n; ++x)
        for (int y = x + 1; y < n; ++y) {
            if (adj[x][y] != 1 || G[x] || G[y]) continue;
            if (x != i && x != j && y != i && y != j) continue;
            if (lexLess(x, y, i, j) && !(x == i && y == j)) return false;
        }
    return true;
}

std::vector<bool> llpLexicallyFirstVertexCover(const std::vector<std::vector<int>>& adj) {
    int n = (int)adj.size();
    std::vector<bool> G(n, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            bool fired = false;
            for (int i = 0; i < n && !fired; ++i)
                if (isLexMinIncident(i, j, adj, G)) fired = true;
            if (fired && !G[j]) { G[j] = true; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> adj = {
        {0, 1, 1, 0, 0},
        {1, 0, 0, 1, 0},
        {1, 0, 0, 1, 0},
        {0, 1, 1, 0, 1},
        {0, 0, 0, 1, 0}
    };
    auto G = llpLexicallyFirstVertexCover(adj);
    std::cout << "cover:";
    for (size_t i = 0; i < G.size(); ++i) if (G[i]) std::cout << ' ' << i;
    std::cout << '\n';
    return 0;
}
