// LLP-LexGreedySetCover: greedy set cover as a lattice-linear predicate.
// A set j is forbidden when it is unpicked, covers at least one still
// uncovered element, and no competing set k beats it -- more coverage, or
// equal coverage and a smaller index. The instance is a pair of incidence
// lists: mem[s] holds the elements of set s, inc[e] the sets containing e.
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
//
// Neither list is an m x u matrix, so the whole instance is O(N) -- N being
// the number of (set, element) incidences -- rather than Theta(m*u), and
// every pass below walks one of them for O(N) instead of O(m*u). That
// matters most for best[], which is rebuilt after every advance: rebuilding
// it by scanning a whole matrix made the algorithm O(m*u) per advance, a
// full factor of n more than the chapter's O(N*min(m,n)) bound.

#include <iostream>
#include <vector>

struct LexGreedySetCover {
    const std::vector<std::vector<int>>& mem;   // mem[s]: elements of set s
    const std::vector<std::vector<int>>& inc;   // inc[e]: sets containing e
    std::vector<bool> G;
    std::vector<bool> covered;
    std::vector<int> cnt;
    std::vector<int> best;
    bool fresh;
    int m, u;

    LexGreedySetCover(const std::vector<std::vector<int>>& memIn,
                      const std::vector<std::vector<int>>& incIn)
        : mem(memIn), inc(incIn), m((int) memIn.size()), u((int) incIn.size()) {
        G.assign(m, false);
        covered.assign(u, false);
        cnt.assign(m, 0);
        best.assign(u, -1);
        fresh = false;
        for (int t = 0; t < m; ++t) cnt[t] = (int) mem[t].size();
    }

    // The unpicked set covering the most uncovered elements that contains
    // e (lowest index on ties, since ties keep whichever came first).
    int bestAt(int e) const {
        int win = -1;
        for (int s : inc[e]) {
            if (!G[s]) {
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
        for (int e : mem[j])
            if (!covered[e] && best[e] != j) return false;
        return true;
    }

    std::vector<bool> run() {
        bool changed = true;
        while (changed) {
            changed = false;
            for (int j = 0; j < m; ++j) {
                if (isLexMaxCov(j)) {
                    G[j] = true;
                    for (int e : mem[j]) {
                        if (!covered[e]) {
                            covered[e] = true;
                            for (int t : inc[e]) --cnt[t];
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

std::vector<bool> llpLexGreedySetCover(const std::vector<std::vector<int>>& mem,
                                      const std::vector<std::vector<int>>& inc) {
    return LexGreedySetCover(mem, inc).run();
}

int main() {
    // {0,1,2}, {1,2,3}, {0,3} over the universe {0,1,2,3}
    std::vector<std::vector<int>> mem = {{0, 1, 2}, {1, 2, 3}, {0, 3}};
    std::vector<std::vector<int>> inc = {{0, 2}, {0, 1}, {0, 1}, {1, 2}};
    std::vector<bool> G = llpLexGreedySetCover(mem, inc);
    std::cout << "chosen sets:";
    for (int j = 0; j < (int) G.size(); ++j) if (G[j]) std::cout << ' ' << j;
    std::cout << '\n';
    return 0;
}
