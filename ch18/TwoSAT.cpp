// 2-SAT via implication graph and SCC detection (Kosaraju's algorithm).
// Literal lit > 0 maps to index lit; literal lit < 0 maps to n + (-lit).

#include <iostream>
#include <vector>

void dfs1(int u, const std::vector<std::vector<int>>& adj,
          std::vector<bool>& visited, std::vector<int>& order) {
    visited[u] = true;
    for (int w : adj[u]) if (!visited[w]) dfs1(w, adj, visited, order);
    order.push_back(u);
}

void dfs2(int u, const std::vector<std::vector<int>>& adj,
          std::vector<int>& comp, int c) {
    comp[u] = c;
    for (int w : adj[u]) if (comp[w] < 0) dfs2(w, adj, comp, c);
}

std::vector<bool> twoSAT(const std::vector<int>& clauseA,
                         const std::vector<int>& clauseB) {
    int m = (int)clauseA.size();
    int n = 0;
    for (int i = 0; i < m; ++i) {
        n = std::max(n, std::abs(clauseA[i]));
        n = std::max(n, std::abs(clauseB[i]));
    }
    int sz = 2 * n + 2;
    auto litIndex = [&](int lit) { return lit > 0 ? lit : n + (-lit); };
    std::vector<std::vector<int>> adjFwd(sz), adjRev(sz);
    for (int i = 0; i < m; ++i) {
        int a = clauseA[i], b = clauseB[i];
        int u1 = litIndex(-a), v1 = litIndex(b);
        adjFwd[u1].push_back(v1); adjRev[v1].push_back(u1);
        int u2 = litIndex(-b), v2 = litIndex(a);
        adjFwd[u2].push_back(v2); adjRev[v2].push_back(u2);
    }
    std::vector<bool> visited(sz, false);
    std::vector<int> order; order.reserve(sz);
    for (int v = 0; v < sz; ++v) if (!visited[v]) dfs1(v, adjFwd, visited, order);
    std::vector<int> comp(sz, -1);
    int numComp = 0;
    for (int idx = (int)order.size() - 1; idx >= 0; --idx) {
        int u = order[idx];
        if (comp[u] < 0) dfs2(u, adjRev, comp, numComp++);
    }
    std::vector<bool> result(n + 1, false);
    for (int xi = 1; xi <= n; ++xi)
        result[xi] = comp[litIndex(xi)] > comp[litIndex(-xi)];
    return result;
}

int main() {
    // (x1 OR x2) AND (~x1 OR x2) AND (x2 OR ~x3)
    std::vector<int> A = { 1, -1,  2};
    std::vector<int> B = { 2,  2, -3};
    auto r = twoSAT(A, B);
    for (size_t i = 1; i < r.size(); ++i)
        std::cout << "x" << i << '=' << (r[i] ? 1 : 0) << ' ';
    std::cout << '\n';
    return 0;
}
