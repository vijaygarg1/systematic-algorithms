// Classical Prim MST: O(n^2) linear-scan version using a weight matrix.

#include <iostream>
#include <vector>
#include <climits>

using Matrix = std::vector<std::vector<int>>;
constexpr int INF = INT_MAX / 2;

std::vector<int> mst(const Matrix& w) {
    int n = (int)w.size();
    std::vector<int>  d(n, INF), parent(n, -1);
    std::vector<bool> fixedFlag(n, false);
    d[0] = 0;
    for (int it = 0; it < n; ++it) {
        int v = -1, best = INF;
        for (int k = 0; k < n; ++k) {
            if (!fixedFlag[k] && d[k] < best) { v = k; best = d[k]; }
        }
        if (v == -1) break;
        fixedFlag[v] = true;
        for (int k = 0; k < n; ++k) {
            if (!fixedFlag[k] && w[v][k] != INF && w[v][k] < d[k]) {
                d[k] = w[v][k];
                parent[k] = v;
            }
        }
    }
    return parent;
}

int main() {
    Matrix w = {
        {0, 1, 3, INF, INF},
        {1, 0, 2, 6, INF},
        {3, 2, 0, 4, 5},
        {INF, 6, 4, 0, 7},
        {INF, INF, 5, 7, 0},
    };
    auto p = mst(w);
    std::cout << "parent:";
    for (int x : p) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
