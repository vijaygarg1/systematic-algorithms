// LLP-WeightedVertexCover: the primal-dual 2-approximation as a
// lattice-linear predicate. The lattice is indexed by EDGES, as the book's
// "G: array[E] of real" and "Forbidden(e)" say: G[e] is the dual charged to
// edge e. An edge is forbidden when both endpoints are slack and it is
// lex-minimal among the slack edges touching either endpoint. A vertex
// joins the cover once its incident duals reach its weight.
//
// The graph arrives as an edge list -- eu[e], ev[e] are the endpoints of
// edge e, with the edges in lex order -- which is the book's
// "E: set of edges". inc[v] lists the edges incident to v, built once from
// that list in O(n + m). No n x n structure appears anywhere: the earlier
// version indexed both the lattice (G[u][v]) and the graph (adj[u][v]) by
// vertex PAIRS, which cost Theta(n^2) in memory and made the sweep
// enumerate all n^2 pairs however few edges the graph had.
//
// Two things keep the predicate cheap. First, price[] is maintained
// incrementally by the advance instead of being re-summed on every call,
// so tight() is O(1) rather than an O(n) sum. Second, the lex-minimality
// test does not scan all edge pairs: with the edges in lex order the
// lex-least slack edge at a vertex is simply the first slack one in
// inc[v], so e is lex-minimal among the slack edges touching it exactly
// when it is first at both ends. minSlackEdge[] is a lazily-refreshed
// cache, rebuilt once after an advance has moved a price, and a rebuild
// consults each vertex's own incident edges only, so it is O(m).

#include <iostream>
#include <vector>
#include <algorithm>

struct WeightedVertexCover {
    const std::vector<int>& eu;
    const std::vector<int>& ev;
    const std::vector<double>& w;
    std::vector<double> G;
    std::vector<double> price;
    std::vector<int> minSlackEdge;
    std::vector<std::vector<int>> inc;   // inc[v]: edges incident to v
    bool fresh;
    int nv, m;

    WeightedVertexCover(const std::vector<int>& eus, const std::vector<int>& evs,
                        const std::vector<double>& ws)
        : eu(eus), ev(evs), w(ws), nv((int) ws.size()), m((int) eus.size()) {
        G.assign(m, 0.0);
        price.assign(nv, 0.0);
        minSlackEdge.assign(nv, -1);
        inc.assign(nv, {});
        for (int e = 0; e < m; ++e) { inc[eu[e]].push_back(e); inc[ev[e]].push_back(e); }
        fresh = false;
    }

    bool tight(int v) const { return price[v] >= w[v]; }

    // Neither endpoint of e is tight, so e's price can still be raised.
    bool slack(int e) const { return !tight(eu[e]) && !tight(ev[e]); }

    // The first slack edge in inc[v], or -1 when v has none. Because the
    // edges are in lex order, "first" is the same as "lex-least".
    int firstSlackEdge(int v) const {
        for (int e : inc[v])
            if (slack(e)) return e;
        return -1;
    }

    void refresh() {
        if (!fresh) {
            for (int v = 0; v < nv; ++v) minSlackEdge[v] = firstSlackEdge(v);
            fresh = true;
        }
    }

    bool slackMin(int e) {
        refresh();
        if (!slack(e)) return false;
        return minSlackEdge[eu[e]] == e && minSlackEdge[ev[e]] == e;
    }

    std::vector<bool> run() {
        bool changed = true;
        while (changed) {
            changed = false;
            for (int e = 0; e < m; ++e) {
                if (slackMin(e)) {
                    double step = std::min(w[eu[e]] - price[eu[e]],
                                          w[ev[e]] - price[ev[e]]);
                    G[e] += step;
                    price[eu[e]] += step;
                    price[ev[e]] += step;
                    fresh = false;
                    changed = true;
                }
            }
        }
        std::vector<bool> C(nv, false);
        for (int i = 0; i < nv; ++i) C[i] = tight(i);
        return C;
    }
};

std::vector<bool> llpWeightedVertexCover(const std::vector<int>& eu,
                                         const std::vector<int>& ev,
                                         const std::vector<double>& w) {
    return WeightedVertexCover(eu, ev, w).run();
}

int main() {
    // path 0-1-2-3, as an edge list in lex order
    std::vector<int> eu = {0, 1, 2};
    std::vector<int> ev = {1, 2, 3};
    std::vector<double> w = {2.0, 1.0, 3.0, 1.5};
    std::vector<bool> C = llpWeightedVertexCover(eu, ev, w);
    std::cout << "cover:";
    for (int i = 0; i < (int) C.size(); ++i) if (C[i]) std::cout << ' ' << i;
    std::cout << '\n';
    return 0;
}
