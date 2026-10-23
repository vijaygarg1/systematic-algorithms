// LLP-LexGreedySetCover: greedy set cover as a lattice-linear predicate.
// A set j is forbidden when it is unpicked, covers at least one still
// uncovered element, and no competing set k beats it -- more coverage, or
// equal coverage and a smaller index. S is an m x u incidence matrix.
//
// The predicate is tested without comparing set pairs. For each uncovered
// element keep best[e], the unpicked set covering the most uncovered
// elements that contains e (lowest index on ties); then j outranks every
// neighbour exactly when best[e] = j for every uncovered e in s_j, since
// the neighbours of j are precisely the unpicked sets sharing such an e.
// The coverage counts cnt[j] = |s_j intersect R| and the covered[] flags
// are maintained by the advance, so coverage is read in O(1) instead of
// being recomputed -- it used to cost an O(m) scan per element, inside an
// O(u) scan per set, inside an O(m) scan over neighbours. best[] is a
// lazily-refreshed cache, rebuilt once per advance rather than once per
// candidate.

#include <iostream>
#include <vector>

struct LexGreedySetCover {
    const std::vector<std::vector<int>>& S;
    std::vector<bool> G;
    std::vector<bool> covered;
    std::vector<int> cnt;
    std::vector<int> best;
    bool fresh;
    int m, u;

    explicit LexGreedySetCover(const std::vector<std::vector<int>>& s)
        : S(s), m((int) s.size()), u(s.empty() ? 0 : (int) s[0].size()) {
        G.assign(m, false);
        covered.assign(u, false);
        cnt.assign(m, 0);
        best.assign(u, -1);
        fresh = false;
        for (int t = 0; t < m; ++t) {
            int c = 0;
            for (int e = 0; e < u; ++e) if (S[t][e] == 1) ++c;
            cnt[t] = c;
        }
    }

    // The unpicked set covering the most uncovered elements that contains
    // e (lowest index on ties, since ties keep whichever came first).
    int bestAt(int e) const {
        int win = -1;
        for (int s = 0; s < m; ++s) {
            if (S[s][e] == 1 && !G[s]) {
                if (win < 0 || cnt[s] > cnt[win]) win = s;
            }
        }
        return win;
    }

    void refresh() {
        if (!fresh) {
            for (int e = 0; e < u; ++e) best[e] = bestAt(e);
            fresh = true;
        }
    }

    bool isLexMaxCov(int j) {
        if (G[j]) return false;
        refresh();
        if (cnt[j] <= 0) return false;
        for (int e = 0; e < u; ++e)
            if (S[j][e] == 1 && !covered[e] && best[e] != j) return false;
        return true;
    }

    std::vector<bool> run() {
        bool changed = true;
        while (changed) {
            changed = false;
            for (int j = 0; j < m; ++j) {
                if (isLexMaxCov(j)) {
                    G[j] = true;
                    for (int e = 0; e < u; ++e) {
                        if (S[j][e] == 1 && !covered[e]) {
                            covered[e] = true;
                            for (int t = 0; t < m; ++t)
                                if (S[t][e] == 1) --cnt[t];
                        }
                    }
                    fresh = false;
                    changed = true;
                }
            }
        }
        return G;
    }
};

std::vector<bool> llpLexGreedySetCover(const std::vector<std::vector<int>>& S) {
    return LexGreedySetCover(S).run();
}

int main() {
    std::vector<std::vector<int>> S = {
        {1, 1, 1, 0},
        {0, 1, 1, 1},
        {1, 0, 0, 1}};
    std::vector<bool> G = llpLexGreedySetCover(S);
    std::cout << "chosen sets:";
    for (int j = 0; j < (int) G.size(); ++j) if (G[j]) std::cout << ' ' << j;
    std::cout << '\n';
    return 0;
}
