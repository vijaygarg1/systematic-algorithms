// LLP-Boruvka: the book's recursive Boruvka(V,E), written as a loop (tail
// recursion) so the pointer-jumping kernel stays a clean fixpoint step.
// Each round selects every active vertex's minimum-weight edge (breaking
// a mutual mwe tie toward the smaller-numbered vertex), pointer-jumps to
// rooted stars, records every active vertex's own mwe edge in the tree,
// keeps only the roots active, and repeats until one component remains.
// `progress` guards against a disconnected graph or an isolated vertex
// (no outgoing edge at all) -- outside the book's own stated
// connected-graph assumption -- so the loop cannot spin forever waiting
// for a spanning tree that cannot exist.

#include <iostream>
#include <limits>
#include <vector>

static int find(int v, std::vector<int>& g) {
    while (g[v] != v) v = g[v];
    return v;
}

static int otherEndpoint(int e, const std::vector<int>& u,
                          const std::vector<int>& v, int x) {
    return u[e] == x ? v[e] : u[e];
}

std::vector<bool> llpBoruvka(int n, std::vector<int> u, std::vector<int> v,
                              std::vector<int> w) {
    int m = (int)u.size();
    std::vector<bool> t(m, false);
    std::vector<int> g(n);
    std::vector<bool> active(n, true);
    for (int i = 0; i < n; ++i) g[i] = i;

    auto countActive = [&]() {
        int c = 0;
        for (int i = 0; i < n; ++i) if (active[i]) ++c;
        return c;
    };

    bool progress = true;
    while (countActive() > 1 && progress) {
        int before = countActive();
        std::vector<int> mweEdge(n, -1);
        std::vector<double> mweW(n, std::numeric_limits<double>::infinity());
        for (int e = 0; e < m; ++e) {
            int ru = find(u[e], g), rv = find(v[e], g);
            if (active[ru] && active[rv] && ru != rv) {
                if (w[e] < mweW[ru]) { mweW[ru] = w[e]; mweEdge[ru] = e; }
                if (w[e] < mweW[rv]) { mweW[rv] = w[e]; mweEdge[rv] = e; }
            }
        }

        for (int i = 0; i < n; ++i) {
            if (active[i]) {
                if (mweEdge[i] == -1) {
                    g[i] = i;
                } else {
                    int nb = otherEndpoint(mweEdge[i], u, v, i);
                    int nbEdge = mweEdge[nb];
                    if (nbEdge != -1 && otherEndpoint(nbEdge, u, v, nb) == i
                            && i < nb) {
                        g[i] = i;
                    } else {
                        g[i] = nb;
                    }
                }
            }
        }

        bool changed = true;
        while (changed) {
            changed = false;
            for (int j = 0; j < n; ++j) {
                if (active[j] && g[j] != g[g[j]]) {
                    g[j] = g[g[j]];
                    changed = true;
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            if (active[i] && mweEdge[i] != -1) t[mweEdge[i]] = true;
        }
        for (int i = 0; i < n; ++i) if (active[i] && g[i] != i) active[i] = false;
        progress = countActive() < before;
    }
    return t;
}

int main() {
    // Same 5-vertex graph as the Kruskal/Prim/Boruvka demos.
    std::vector<int> u = {1, 0, 0, 1, 2, 2, 3};
    std::vector<int> v = {2, 1, 2, 3, 3, 4, 4};
    std::vector<int> w = {1, 2, 3, 4, 5, 6, 7};
    auto t = llpBoruvka(5, u, v, w);
    std::cout << "[";
    for (size_t e = 0; e < t.size(); ++e) {
        std::cout << (t[e] ? "true" : "false");
        if (e + 1 < t.size()) std::cout << ", ";
    }
    std::cout << "]\n";
    return 0;
}
