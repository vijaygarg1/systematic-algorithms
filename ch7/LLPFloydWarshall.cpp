// Companion-only program, like its .llp and .java siblings: the book
// presents LLP-FloydWarshall only as LaTeX pseudocode, so there is no
// book/lang/progs-shortestPath/ counterpart to sync from. See
// COMPANION_ONLY_STEMS in sync-code-from-lang.py -- this file must be
// preserved there or a sync run will delete it.
//
// Revised to match the book's staged formulation (bxx-sp-llp-apsp.tex):
// the intermediate vertex k is processed in n sequential stages
// k = 0, ..., n-1, each one ensure(i,j): G[i][j] <= G[i][k]+G[k][j]
// against that single k -- a single deterministic check-and-lower per
// pair, not a fixpoint, since G[i][k] and G[k][j] do not change during
// stage k (the book's correctness argument). This is O(n^3), unlike the
// old unordered-rescan version this replaces, which cost an extra
// factor of n.

#include <iostream>
#include <vector>
#include <limits>

std::vector<std::vector<int>> llpFloydWarshall(
        const std::vector<std::vector<int>>& A) {
    int n = (int) A.size();
    const int INF = std::numeric_limits<int>::max();
    std::vector<std::vector<int>> G(n, std::vector<int>(n));
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
            G[i][j] = A[i][j];
    for (int k = 0; k < n; ++k) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (G[i][k] != INF && G[k][j] != INF) {
                    int viaK = G[i][k] + G[k][j];
                    if (viaK < G[i][j]) G[i][j] = viaK;
                }
            }
        }
    }
    return G;
}

int main() {
    const int INF = 1000000;
    std::vector<std::vector<int>> A = {
        {0, 3, INF, INF},
        {INF, 0, 4, INF},
        {INF, INF, 0, 2},
        {INF, INF, INF, 0}};
    std::vector<std::vector<int>> G = llpFloydWarshall(A);
    std::cout << "shortest 0->3 = " << G[0][3] << '\n';
    return 0;
}
