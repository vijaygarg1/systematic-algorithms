// König's theorem: minimum vertex cover of size |M| from a maximum
// bipartite matching, via alternating reachability. Matches
// bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set
// of vertices reachable from an unmatched L-vertex by an alternating path
// (non-matching edge, then matching edge, ...); C := (L \ Z) union (R
// intersect Z).

#include <iostream>
#include <vector>

std::vector<bool> parVertexCoverFromMatching(const std::vector<std::vector<int>>& adj,
                                             const std::vector<int>& matchL) {
    int L = (int)adj.size();
    int R = (int)adj[0].size();
    std::vector<bool> inZ(L + R, false);
    std::vector<int>  partner(L + R, -1);

    for (int u = 0; u < L; ++u) {
        int v = matchL[u];
        if (v != -1) {
            partner[u] = L + v;
            partner[L + v] = u;
        }
    }

    // BFS queue of vertices whose incident edges are still unexplored,
    // seeded with every unmatched L-vertex.
    std::vector<int> Q;
    for (int u = 0; u < L; ++u) {
        if (matchL[u] == -1) {
            inZ[u] = true;
            Q.push_back(u);
        }
    }

    for (std::size_t head = 0; head < Q.size(); ++head) {
        int w = Q[head];
        if (w < L) {
            // From an L-vertex, follow every non-matching edge.
            for (int v = 0; v < R; ++v) {
                if (adj[w][v] == 1 && partner[w] != L + v && !inZ[L + v]) {
                    inZ[L + v] = true;
                    Q.push_back(L + v);
                }
            }
        } else {
            // From an R-vertex, follow its matching edge (if any).
            int p = partner[w];
            if (p != -1 && !inZ[p]) {
                inZ[p] = true;
                Q.push_back(p);
            }
        }
    }

    std::vector<bool> C(L + R, false);
    for (int u = 0; u < L; ++u) C[u] = !inZ[u];
    for (int v = 0; v < R; ++v) C[L + v] = inZ[L + v];
    return C;
}

int main() {
    std::vector<std::vector<int>> adj = {
        {1, 1, 0},
        {1, 0, 1},
        {0, 1, 1}
    };
    std::vector<int> matchL = {0, 2, 1};
    auto C = parVertexCoverFromMatching(adj, matchL);
    std::cout << "cover bits:";
    for (bool b : C) std::cout << ' ' << (b ? 1 : 0);
    std::cout << '\n';
    return 0;
}
