// LLP-HornSAT: forward chaining as a lattice-linear predicate. A variable
// is forbidden when some definite clause has all antecedents true and it
// as a false consequent; a pure negative clause (head == -1) firing means
// the formula is unsatisfiable, reported here as an empty result.

#include <iostream>
#include <vector>

struct HornSAT {
    const std::vector<std::vector<int>>& body;
    const std::vector<int>& head;
    std::vector<bool> G;
    int n;

    HornSAT(const std::vector<std::vector<int>>& b,
            const std::vector<int>& h, int nn)
        : body(b), head(h), n(nn) { G.assign(n, false); }

    // Every antecedent of clause c is true.
    bool allTrue(int c) const {
        for (size_t k = 0; k < body[c].size(); ++k)
            if (!G[body[c][k]]) return false;
        return true;
    }

    // Clauses indexed by consequent, built once: scanning every clause
    // looking for head[c] == j made one sweep O(n * c) instead of O(c).
    std::vector<std::vector<int>> byHead;

    void buildIndex() {
        byHead.assign(n, {});
        for (size_t c = 0; c < body.size(); ++c)
            if (head[c] >= 0) byHead[head[c]].push_back((int) c);
    }

    bool definiteImplied(int j) const {
        if (G[j]) return false;
        for (size_t t = 0; t < byHead[j].size(); ++t)
            if (allTrue(byHead[j][t])) return true;
        return false;
    }

    // Some pure negative clause has all its antecedents true.
    bool goalFired() const {
        for (size_t c = 0; c < body.size(); ++c)
            if (head[c] == -1 && allTrue((int) c)) return true;
        return false;
    }

    // Empty vector means the `return null` of the .llp: unsatisfiable.
    std::vector<bool> run() {
        buildIndex();
        bool changed = true;
        while (changed) {
            changed = false;
            for (int j = 0; j < n; ++j) {
                if (definiteImplied(j)) { G[j] = true; changed = true; }
            }
        }
        // Goal clauses are checked once, after the least model is reached.
        // G only grows and allTrue is monotone in G, so a goal clause that
        // fires at any point is still firing at the end. As a rule of the
        // fixpoint it ran once per index per sweep for a value that does
        // not depend on the index.
        if (goalFired()) return std::vector<bool>();
        return G;
    }
};

std::vector<bool> llpHornSAT(const std::vector<std::vector<int>>& body,
                             const std::vector<int>& head, int n) {
    return HornSAT(body, head, n).run();
}

int main() {
    std::vector<std::vector<int>> body = {{}, {0}, {0, 1}};
    std::vector<int> head = {0, 1, 2};
    std::vector<bool> G = llpHornSAT(body, head, 3);
    std::cout << "true variables:";
    for (int j = 0; j < (int) G.size(); ++j) if (G[j]) std::cout << ' ' << j;
    std::cout << '\n';
    return 0;
}
