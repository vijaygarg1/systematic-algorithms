// 2-approximation vertex cover: greedily pick both endpoints of an
// uncovered edge.

#include <iostream>
#include <vector>

std::vector<bool> approxVertexCover(const std::vector<std::vector<int>>& adj) {
    int n = (int)adj.size();
    std::vector<bool> C(n, false), removed(n, false);
    bool done = false;
    while (!done) {
        done = true;
        for (int u = 0; u < n; ++u) {
            if (removed[u]) continue;
            for (int v = u + 1; v < n; ++v) {
                if (!removed[v] && adj[u][v] == 1) {
                    C[u] = C[v] = true;
                    removed[u] = removed[v] = true;
                    done = false;
                    break;
                }
            }
        }
    }
    return C;
}

int main() {
    std::vector<std::vector<int>> adj = {
        {0, 1, 1, 0, 0},
        {1, 0, 0, 1, 0},
        {1, 0, 0, 1, 0},
        {0, 1, 1, 0, 1},
        {0, 0, 0, 1, 0}
    };
    auto C = approxVertexCover(adj);
    std::cout << "cover:";
    for (size_t i = 0; i < C.size(); ++i) if (C[i]) std::cout << ' ' << i;
    std::cout << '\n';
    return 0;
}
