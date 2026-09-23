// LLP-Kruskal: edge-inclusion lattice driven by union-find.

#include <iostream>
#include <vector>

int find(int x, std::vector<int>& parent) {
    while (parent[x] != x) {
        parent[x] = parent[parent[x]];        // path-halving
        x = parent[x];
    }
    return x;
}

void unionFind(int a, int b, std::vector<int>& parent) {
    int ra = find(a, parent), rb = find(b, parent);
    if (ra != rb) parent[ra] = rb;
}

std::vector<bool> llpKruskal(const std::vector<int>& u,
                             const std::vector<int>& v,
                             std::vector<int>& parent) {
    int m = (int)u.size();
    std::vector<bool> C(m, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < m; ++j) {
            if (!C[j] && find(u[j], parent) != find(v[j], parent)) {
                C[j] = true;
                unionFind(u[j], v[j], parent);
                changed = true;
            }
        }
    }
    return C;
}

int main() {
    std::vector<int> u = {0, 1, 0, 1, 2};
    std::vector<int> v = {1, 2, 2, 3, 3};
    int n = 4;
    std::vector<int> parent(n);
    for (int i = 0; i < n; ++i) parent[i] = i;
    auto C = llpKruskal(u, v, parent);
    std::cout << "C:";
    for (bool b : C) std::cout << ' ' << (b ? "true" : "false");
    std::cout << '\n';
    return 0;
}
