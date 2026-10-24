// Classical Prim MST (bxx-mst.tex, algo:prim): binary min-heap of
// (cost, vertex) keyed by cost, with lazy deletion -- a vertex may be
// pushed more than once as its cost-to-fragment falls, and stale entries
// are skipped on removal via the fixed[] check.
//
// The graph is a weighted adjacency list: adj[v] holds the neighbours of
// v and the parallel aw[v] their edge weights, so the weight of
// (v, adj[v][t]) is aw[v][t].  No n x n matrix is involved; the whole
// structure is O(n + m).
//
// The relaxation loop walks adj[v], which is the book's
// "forall v': not fixed[v'] and (v,v') in E". With the heap supplying the minimum this
// is the O(m log n) variant of Table 8.1, not the O(n^2) array-scan
// variant of the surrounding text (bxx-mst.tex:1009) -- that one finds
// the minimum by scanning d[] rather than with a heap. This file used to
// be the array-scan version while the .llp was already the heap one, so
// the two language ports implemented different algorithms.

#include <iostream>
#include <queue>
#include <vector>
#include <climits>

using Matrix = std::vector<std::vector<int>>;
constexpr int INF = INT_MAX / 2;

std::vector<int> mst(const std::vector<std::vector<int>>& adj,
                     const std::vector<std::vector<int>>& aw) {
    int n = (int) adj.size();
    std::vector<int>  d(n, INF), parent(n, -1);
    std::vector<bool> fixedFlag(n, false);
    d[0] = 0;
    std::priority_queue<std::pair<int, int>,
                        std::vector<std::pair<int, int>>,
                        std::greater<std::pair<int, int>>> H;
    H.push({d[0], 0});
    int count = 0;
    while (count < n) {
        if (H.empty()) return parent;      // graph disconnected: no spanning tree
        int v = H.top().second;
        H.pop();
        if (fixedFlag[v]) continue;
        fixedFlag[v] = true;
        ++count;
        for (size_t t = 0; t < adj[v].size(); ++t) {
            int k = adj[v][t];
            if (!fixedFlag[k] && aw[v][t] < d[k]) {
                d[k] = aw[v][t];
                parent[k] = v;
                H.push({d[k], k});
            }
        }
    }
    return parent;
}

int main() {
    std::vector<std::vector<int>> adj = {
        {1, 2}, {0, 2, 3}, {0, 1, 3, 4}, {1, 2, 4}, {2, 3},
    };
    std::vector<std::vector<int>> aw = {
        {1, 3}, {1, 2, 6}, {3, 2, 4, 5}, {6, 4, 7}, {5, 7},
    };
    auto p = mst(adj, aw);
    std::cout << "parent:";
    for (int x : p) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
