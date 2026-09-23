// Classical Boruvka MST: repeatedly attach every component to its
// cheapest outgoing edge until one component remains.

#include <iostream>
#include <vector>
#include <queue>
#include <limits>

static int neighborIf(int a, int b, int v) {
    if (a == v) return b;
    if (b == v) return a;
    return -1;
}

static std::vector<int> components(int n, const std::vector<int>& U,
                                    const std::vector<int>& V,
                                    const std::vector<bool>& inTree) {
    std::vector<int> cid(n, -1);
    std::vector<bool> visited(n, false);
    for (int start = 0; start < n; ++start) {
        if (visited[start]) continue;
        visited[start] = true;
        cid[start] = start;
        std::queue<int> q;
        q.push(start);
        while (!q.empty()) {
            int v = q.front(); q.pop();
            for (size_t e = 0; e < U.size(); ++e) {
                if (!inTree[e]) continue;
                int u = neighborIf(U[e], V[e], v);
                if (u != -1 && !visited[u]) {
                    visited[u] = true;
                    cid[u] = cid[start];
                    q.push(u);
                }
            }
        }
    }
    return cid;
}

std::vector<bool> mst(int n, const std::vector<int>& U,
                            const std::vector<int>& V,
                            const std::vector<double>& W) {
    int m = (int)U.size();
    std::vector<bool> inTree(m, false);
    int treeEdges = 0;
    while (treeEdges < n - 1) {
        std::vector<int> cid = components(n, U, V, inTree);

        std::vector<int> mwe(n, -1);
        std::vector<double> dist(n, std::numeric_limits<double>::infinity());
        for (int e = 0; e < m; ++e) {
            int i = U[e], j = V[e];
            if (cid[i] != cid[j]) {
                if (W[e] < dist[cid[i]]) { dist[cid[i]] = W[e]; mwe[cid[i]] = e; }
                if (W[e] < dist[cid[j]]) { dist[cid[j]] = W[e]; mwe[cid[j]] = e; }
            }
        }

        for (int i = 0; i < n; ++i) {
            if (cid[i] == i && mwe[i] != -1 && !inTree[mwe[i]]) {
                inTree[mwe[i]] = true;
                ++treeEdges;
            }
        }
    }
    return inTree;
}

int main() {
    int n = 5;
    std::vector<int> U = {0, 1, 0, 3, 1, 2};
    std::vector<int> V = {2, 2, 3, 4, 3, 4};
    std::vector<double> W = {4, 3, 7, 2, 9, 11};
    auto inTree = mst(n, U, V, W);
    std::cout << "inTree:";
    for (bool b : inTree) std::cout << ' ' << (b ? "true" : "false");
    std::cout << '\n';
    return 0;
}
