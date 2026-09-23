// König's-theorem construction of a vertex cover of size |M| from a
// max matching.

#include <iostream>
#include <vector>

std::vector<bool> parVertexCoverFromMatching(const std::vector<std::vector<int>>& adj,
                                             const std::vector<int>& matchL) {
    int L = (int)adj.size();
    int R = (int)adj[0].size();
    std::vector<bool> C(L + R, false);
    std::vector<int>  partner(L + R, -1);

    for (int u = 0; u < L; ++u) {
        int v = matchL[u];
        if (v != -1) {
            C[u] = true;
            partner[u] = L + v;
            partner[L + v] = u;
        }
    }

    for (int u = 0; u < L; ++u) {
        for (int v = 0; v < R; ++v) {
            if (adj[u][v] == 1 && !C[u] && !C[L + v]) {
                if (partner[u] != -1) {
                    C[partner[u]] = false;
                    C[u] = true;
                } else {
                    C[partner[L + v]] = false;
                    C[L + v] = true;
                }
            }
        }
    }
    return C;
}

int main() {
    std::vector<std::vector<int>> adj = {
        {1, 1, 0},
        {1, 0, 1},
        {0, 1, 1}
    };
    std::vector<int> matchL = {0, 2, 1};
    auto C = parVertexCoverFromMatching(adj, matchL);
    std::cout << "cover bits:";
    for (bool b : C) std::cout << ' ' << (b ? 1 : 0);
    std::cout << '\n';
    return 0;
}
