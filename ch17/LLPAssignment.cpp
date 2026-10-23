// LLP-Assignment: the auction/Hungarian price-raising algorithm as a
// lattice-linear predicate. G[i] is the price of item i; item j is
// forbidden when it lies in the witness set J of overdemanded items, and
// the advance raises every item of J by the one shared slack jump.
//
// itemInJ/bidderInB are rebuilt lazily (the `fresh` flag) because every
// guard evaluation needs them and they only change when G moves: match
// the tight-edge graph, then mark everything alternating-reachable from a
// bidder the matching leaves unmatched.

#include <iostream>
#include <vector>
#include <limits>

struct Assignment {
    const std::vector<std::vector<int>>& v;
    int n, m;
    std::vector<int> G, partner;
    std::vector<bool> itemInJ, bidderInB;
    bool fresh;

    explicit Assignment(const std::vector<std::vector<int>>& val)
        : v(val), m((int) val.size()),
          fresh(false) {
        n = val.empty() ? 0 : (int) val[0].size();
        G.assign(n, 0);
        partner.assign(n, -1);
        itemInJ.assign(n, false);
        bidderInB.assign(m, false);
    }

    int bestSurplus(int b) const {
        int best = std::numeric_limits<int>::min();
        for (int i = 0; i < n; ++i) best = std::max(best, v[b][i] - G[i]);
        return best;
    }

    // bestSurplus restricted to the items outside J.
    int bestOutside(int b) const {
        int best = std::numeric_limits<int>::min();
        for (int i = 0; i < n; ++i)
            if (!itemInJ[i] && v[b][i] - G[i] > best) best = v[b][i] - G[i];
        return best;
    }

    bool tryMatch(int b, std::vector<bool>& seen) {
        int best = bestSurplus(b);
        for (int i = 0; i < n; ++i) {
            if (v[b][i] - G[i] == best && !seen[i]) {
                seen[i] = true;
                if (partner[i] == -1 || tryMatch(partner[i], seen)) {
                    partner[i] = b;
                    return true;
                }
            }
        }
        return false;
    }

    // The lowest bidder the matching leaves unmatched, or m when perfect.
    int firstUnmatched() const {
        for (int b = 0; b < m; ++b) {
            bool matched = false;
            for (int i = 0; i < n && !matched; ++i) if (partner[i] == b) matched = true;
            if (!matched) return b;
        }
        return m;
    }

    // Mark every item and bidder alternating-reachable from bidder b.
    void reach(int b) {
        if (bidderInB[b]) return;
        bidderInB[b] = true;
        int best = bestSurplus(b);
        for (int i = 0; i < n; ++i) {
            if (v[b][i] - G[i] == best && !itemInJ[i]) {
                itemInJ[i] = true;
                if (partner[i] != -1) reach(partner[i]);
            }
        }
    }

    void refresh() {
        if (fresh) return;
        for (int i = 0; i < n; ++i) { itemInJ[i] = false; partner[i] = -1; }
        for (int b = 0; b < m; ++b) bidderInB[b] = false;
        for (int b = 0; b < m; ++b) {
            std::vector<bool> seen(n, false);
            tryMatch(b, seen);
        }
        int u = firstUnmatched();
        if (u < m) reach(u);
        fresh = true;
    }

    bool overdemanded(int j) { refresh(); return itemInJ[j]; }

    // Raise every item of J by the least drop, over the bidders demanding
    // into J, from their best surplus to their best surplus outside J.
    void raiseJ() {
        int delta = std::numeric_limits<int>::max();
        for (int b = 0; b < m; ++b) {
            if (bidderInB[b]) {
                int drop = bestSurplus(b) - bestOutside(b);
                if (drop < delta) delta = drop;
            }
        }
        for (int i = 0; i < n; ++i) if (itemInJ[i]) G[i] += delta;
        fresh = false;
    }

    std::vector<int> run() {
        bool changed = true;
        while (changed) {
            changed = false;
            for (int j = 0; j < n; ++j) {
                if (overdemanded(j)) { raiseJ(); changed = true; }
            }
        }
        return G;
    }
};

std::vector<int> llpAssignment(const std::vector<std::vector<int>>& v) {
    return Assignment(v).run();
}

int main() {
    std::vector<std::vector<int>> v = {
        {10, 0},
        {0, 10}};
    std::vector<int> G = llpAssignment(v);
    std::cout << "prices:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
