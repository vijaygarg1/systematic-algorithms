// Classical Kruskal MST: edges already sorted by weight.

#include <iostream>
#include <vector>

int find(std::vector<int>& parent, int x) {
    if (parent[x] != x) parent[x] = find(parent, parent[x]);
    return parent[x];
}

bool unionSets(std::vector<int>& parent, std::vector<int>& rank_, int x, int y) {
    int rx = find(parent, x), ry = find(parent, y);
    if (rx == ry) return false;
    if      (rank_[rx] < rank_[ry])  parent[rx] = ry;
    else if (rank_[rx] > rank_[ry])  parent[ry] = rx;
    else { parent[ry] = rx; ++rank_[rx]; }
    return true;
}

std::vector<bool> mst(int n, const std::vector<int>& U,
                            const std::vector<int>& V,
                            const std::vector<int>& /*W*/) {
    int m = (int)U.size();
    std::vector<bool> inTree(m, false);
    std::vector<int>  parent(n), rank_(n, 0);
    for (int i = 0; i < n; ++i) parent[i] = i;
    int chosen = 0;
    for (int e = 0; e < m && chosen < n - 1; ++e) {
        if (unionSets(parent, rank_, U[e], V[e])) {
            inTree[e] = true;
            ++chosen;
        }
    }
    return inTree;
}

int main() {
    int n = 4;
    std::vector<int> U = {0, 1, 0, 1, 2};
    std::vector<int> V = {1, 2, 2, 3, 3};
    std::vector<int> W = {1, 2, 3, 4, 5};
    auto inTree = mst(n, U, V, W);
    std::cout << "inTree:";
    for (bool b : inTree) std::cout << ' ' << (b ? "true" : "false");
    std::cout << '\n';
    return 0;
}
