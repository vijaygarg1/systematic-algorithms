// LLP-WeightedVertexCover: the primal-dual 2-approximation as a
// lattice-linear predicate. G[u][v] is the dual charged to edge (u, v);
// an edge is forbidden when both endpoints are slack and it is
// lex-minimal among the slack edges touching either endpoint. A vertex
// joins the cover once its incident duals reach its weight.
//
// Two things keep the predicate cheap. First, price[] is maintained
// incrementally by the advance instead of being re-summed on every call,
// so tight() is O(1) rather than an O(n) sum. Second, the lex-minimality
// test does not scan all edge pairs: the lex-least slack edge at a vertex
// u is always {u, minSlack[u]}, where minSlack[u] is u's smallest
// non-tight neighbour (a neighbour y < u contributes the key (y,u) and
// y > u the key (u,y), so the smallest index wins either way), so {u,v}
// is lex-minimal among the slack edges touching it exactly when it is the
// lex-least at both ends. minSlack[] is a lazily-refreshed cache, rebuilt
// only once after an advance has moved a price, not once per candidate.

#include <iostream>
#include <vector>
#include <algorithm>

struct WeightedVertexCover {
    const std::vector<std::vector<int>>& adj;
    const std::vector<double>& w;
    std::vector<std::vector<double>> G;
    std::vector<double> price;
    std::vector<int> minSlack;
    bool fresh;
    int nv;

    WeightedVertexCover(const std::vector<std::vector<int>>& a,
                        const std::vector<double>& ws)
        : adj(a), w(ws), nv((int) ws.size()) {
        G.assign(nv, std::vector<double>(nv, 0.0));
        price.assign(nv, 0.0);
        minSlack.assign(nv, -1);
        fresh = false;
    }

    bool tight(int v) const { return price[v] >= w[v]; }

    // The smallest non-tight neighbour of u, or -1 when u has none.
    int firstSlackNbr(int u) const {
        for (int y = 0; y < nv; ++y)
            if (adj[u][y] == 1 && !tight(y)) return y;
        return -1;
    }

    void refresh() {
        if (!fresh) {
            for (int u = 0; u < nv; ++u) minSlack[u] = firstSlackNbr(u);
            fresh = true;
        }
    }

    bool slackMin(int u, int v) {
        if (adj[u][v] != 1) return false;
        refresh();
        if (tight(u) || tight(v)) return false;
        return minSlack[u] == v && minSlack[v] == u;
    }

    std::vector<bool> run() {
        bool changed = true;
        while (changed) {
            changed = false;
            for (int u = 0; u < nv; ++u) {
                for (int v = 0; v < nv; ++v) {
                    if (u < v && slackMin(u, v)) {
                        double step = std::min(w[u] - price[u], w[v] - price[v]);
                        G[u][v] += step;
                        G[v][u] = G[u][v];
                        price[u] += step;
                        price[v] += step;
                        fresh = false;
                        changed = true;
                    }
                }
            }
        }
        std::vector<bool> C(nv, false);
        for (int i = 0; i < nv; ++i) C[i] = tight(i);
        return C;
    }
};

std::vector<bool> llpWeightedVertexCover(const std::vector<std::vector<int>>& adj,
                                         const std::vector<double>& w) {
    return WeightedVertexCover(adj, w).run();
}

int main() {
    std::vector<std::vector<int>> adj = {
        {0, 1, 0, 0},
        {1, 0, 1, 0},
        {0, 1, 0, 1},
        {0, 0, 1, 0}};
    std::vector<double> w = {2.0, 1.0, 3.0, 1.5};
    std::vector<bool> C = llpWeightedVertexCover(adj, w);
    std::cout << "cover:";
    for (int i = 0; i < (int) C.size(); ++i) if (C[i]) std::cout << ' ' << i;
    std::cout << '\n';
    return 0;
}
