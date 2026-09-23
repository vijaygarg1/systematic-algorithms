// Disjoint-set with path compression in find and union-by-rank.

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

int main() {
    int n = 6;
    std::vector<int> parent(n), rank_(n, 0);
    for (int i = 0; i < n; ++i) parent[i] = i;
    int pairs[][2] = {{0,1}, {2,3}, {1,2}};
    for (auto& p : pairs) {
        bool merged = unionSets(parent, rank_, p[0], p[1]);
        std::cout << "union(" << p[0] << ", " << p[1] << ") -> merged="
                  << (merged ? "true" : "false") << '\n';
    }
    std::cout << "roots:";
    for (int i = 0; i < n; ++i) std::cout << ' ' << find(parent, i);
    std::cout << '\n';
    return 0;
}
