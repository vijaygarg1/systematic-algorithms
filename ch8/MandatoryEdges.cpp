// MandatoryEdges: composition program forcing the edges in subset M
// into the spanning tree.  On infeasibility returns an empty vector.

#include <iostream>
#include <vector>

static int find(std::vector<int>& parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}

static void unite(std::vector<int>& parent, int a, int b) {
    int ra = find(parent, a), rb = find(parent, b);
    if (ra != rb) parent[ra] = rb;
}

std::vector<bool> mandatoryEdges(const std::vector<int>& u,
                                 const std::vector<int>& v,
                                 const std::vector<bool>& M,
                                 int n) {
    int m = (int)u.size();
    std::vector<int> parent(n);
    for (int i = 0; i < n; ++i) parent[i] = i;
    std::vector<bool> G(m, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < m; ++j) {
            if (M[j] && !G[j]) {
                if (find(parent, u[j]) == find(parent, v[j])) return {};
                G[j] = true; unite(parent, u[j], v[j]); changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<int> u = {0, 1, 0};
    std::vector<int> v = {1, 2, 2};
    std::vector<bool> M = {true, true, true};
    auto G = mandatoryEdges(u, v, M, 3);
    if (G.empty()) { std::cout << "infeasible\n"; return 0; }
    std::cout << "G:";
    for (size_t j = 0; j < G.size(); ++j) std::cout << ' ' << (int)G[j];
    std::cout << '\n';
    return 0;
}
