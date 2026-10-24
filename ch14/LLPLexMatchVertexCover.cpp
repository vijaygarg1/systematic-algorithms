// LLP-LexMatchVertexCover: the maximal-matching 2-approximation as a
// lattice-linear predicate. adj is an n x n 0/1 adjacency matrix and edges
// are ordered by (min(u,v), max(u,v)).
//
// The lex-least uncovered edge incident to a vertex u is always
// {u, minNbr(u)}, where minNbr(u) is u's smallest uncovered neighbour: a
// neighbour y < u contributes the key (y, u) and a neighbour y > u the key
// (u, y), so in both cases the smallest index wins. An edge is therefore
// lex-minimal among the edges touching it exactly when it is the lex-least
// edge at BOTH of its endpoints, which is what isLexMin tests with two O(n)
// scans -- replacing the old all-pairs isLexMinIncident check, which cost
// an O(n^2) scan over all edge pairs per candidate.
//
// The advance covers BOTH endpoints of the selected edge. Marking only j
// would leave its partner i uncovered, and i's own test then sees a
// different minNbr and fails -- the selected edges stop being a matching
// and the 2-approximation bound is lost (on a four-leaf star the old,
// j-only version returned a three-vertex cover against an optimum of one).

#include <iostream>
#include <vector>

struct LexMatchVertexCover {
    const std::vector<std::vector<int>>& adj;
    std::vector<bool> G;
    int n;

    explicit LexMatchVertexCover(const std::vector<std::vector<int>>& a)
        : adj(a), n((int) a.size()) { G.assign(n, false); }

    // The smallest uncovered neighbour of u, or -1 when u has none.
    int minNbr(int u) const {
        for (int y : adj[u])
            if (!G[y]) return y;
        return -1;
    }

    // j is an endpoint of the lex-minimal uncovered edge {j, i}: i is j's
    // smallest uncovered neighbour and j is i's.
    bool isLexMin(int j) const {
        if (G[j]) return false;
        int i = minNbr(j);
        if (i < 0) return false;
        return minNbr(i) == j;
    }

    std::vector<bool> run() {
        bool changed = true;
        while (changed) {
            changed = false;
            for (int j = 0; j < n; ++j) {
                if (isLexMin(j)) {
                    int i = minNbr(j);
                    G[j] = true;
                    G[i] = true;
                    changed = true;
                }
            }
        }
        return G;
    }
};

std::vector<bool> llpLexMatchVertexCover(const std::vector<std::vector<int>>& adj) {
    return LexMatchVertexCover(adj).run();
}

int main() {
    // path 0-1-2-3, as sorted adjacency lists
    std::vector<std::vector<int>> adj = {{1}, {0, 2}, {1, 3}, {2}};
    std::vector<bool> G = llpLexMatchVertexCover(adj);
    std::cout << "cover:";
    for (int j = 0; j < (int) G.size(); ++j) if (G[j]) std::cout << ' ' << j;
    std::cout << '\n';
    return 0;
}
