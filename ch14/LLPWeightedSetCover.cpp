// LLP-WeightedSetCover: the primal-dual price-raising algorithm as a
// lattice-linear predicate. G[x] is the price on element x; an element is
// forbidden when it is covered by some set, slack (lies in no tight set),
// and lex-minimal among the slack elements sharing a set with it. The
// advance raises its price by the smallest remaining budget among the
// sets containing it. A set is chosen when its prices reach its weight.
//
// The covered(x) guard matters: an element that lies in no set at all is
// slack forever (it can never belong to a tight set), so without the
// guard the loop would raise its price without end. With it, an
// uncoverable element simply keeps price 0.
//
// Three cached quantities keep the predicate off the critical path. The
// set prices sp[] are maintained incrementally by the advance, so
// setTight is O(1) instead of an O(u) sum; slack[] records which elements
// lie in no tight set; and minSlackIn[s] is the smallest slack element of
// s, which turns "no slack element below x shares a set with x" into a
// scan of the sets containing x rather than a scan over all smaller
// elements, each re-deciding slackness. All three are lazily-refreshed
// caches, rebuilt once per advance rather than once per candidate.

#include <iostream>
#include <vector>
#include <limits>

struct WeightedSetCover {
    const std::vector<std::vector<int>>& mem;   // mem[s]: elements of set s
    const std::vector<std::vector<int>>& inc;   // inc[e]: sets containing e
    const std::vector<double>& w;
    std::vector<double> G;
    std::vector<double> sp;
    std::vector<bool> slack;
    std::vector<int> minSlackIn;
    bool fresh;
    int m, u;

    WeightedSetCover(const std::vector<std::vector<int>>& memIn,
                     const std::vector<std::vector<int>>& incIn,
                     const std::vector<double>& ws)
        : mem(memIn), inc(incIn), w(ws), m((int) ws.size()),
          u((int) incIn.size()) {
        G.assign(u, 0.0);
        sp.assign(m, 0.0);
        slack.assign(u, false);
        minSlackIn.assign(m, u);
        fresh = false;
    }

    // x belongs to at least one set.
    bool covered(int x) const {
        return !inc[x].empty();
        return false;
    }

    bool setTight(int s) const { return sp[s] >= w[s]; }

    bool isSlack(int e) const {
        for (int s : inc[e])
            if (setTight(s)) return false;
        return true;
    }

    // The smallest slack element of set s, or u if it has none.
    int firstSlackOf(int s) const {
        for (int e : mem[s])
            if (slack[e]) return e;
        return u;
    }

    void refresh() {
        if (!fresh) {
            for (int e = 0; e < u; ++e) slack[e] = isSlack(e);
            for (int s = 0; s < m; ++s) minSlackIn[s] = firstSlackOf(s);
            fresh = true;
        }
    }

    bool slackMin(int x) {
        refresh();
        if (!slack[x]) return false;
        for (int s : inc[x])
            if (minSlackIn[s] < x) return false;
        return true;
    }

    // Smallest remaining budget among the sets containing element e.
    double minRemainingBudget(int e) const {
        double best = std::numeric_limits<double>::infinity();
        for (int s : inc[e]) {
            double budget = w[s] - sp[s];
            if (budget < best) best = budget;
        }
        return best;
    }

    std::vector<bool> run() {
        bool changed = true;
        while (changed) {
            changed = false;
            for (int x = 0; x < u; ++x) {
                if (covered(x) && slackMin(x)) {
                    double step = minRemainingBudget(x);
                    G[x] += step;
                    for (int s : inc[x]) sp[s] += step;
                    fresh = false;
                    changed = true;
                }
            }
        }
        std::vector<bool> C(m, false);
        for (int s = 0; s < m; ++s) C[s] = setTight(s);
        return C;
    }
};

std::vector<bool> llpWeightedSetCover(const std::vector<std::vector<int>>& mem,
                                     const std::vector<std::vector<int>>& inc,
                                      const std::vector<double>& w) {
    return WeightedSetCover(mem, inc, w).run();
}

int main() {
    // {0,1,2}, {1,2,3}, {0,3} over the universe {0,1,2,3}
    std::vector<std::vector<int>> mem = {{0, 1, 2}, {1, 2, 3}, {0, 3}};
    std::vector<std::vector<int>> inc = {{0, 2}, {0, 1}, {0, 1}, {1, 2}};
    std::vector<double> w = {3.0, 2.0, 2.5};
    std::vector<bool> C = llpWeightedSetCover(mem, inc, w);
    std::cout << "chosen sets:";
    for (int s = 0; s < (int) C.size(); ++s) if (C[s]) std::cout << ' ' << s;
    std::cout << '\n';
    return 0;
}
