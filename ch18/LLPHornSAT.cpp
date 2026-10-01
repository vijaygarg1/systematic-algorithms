// LLP Horn SAT. head[c] is the consequent variable of clause c, or -1
// if clause c is a pure negative (goal) clause (antecedents => false).
// Rule 1: a definite clause with all antecedents true and a false
// consequent forces that consequent true. Rule 2: a goal clause with
// all antecedents true proves unsatisfiability.

#include <iostream>
#include <vector>
#include <optional>

bool allTrue(const std::vector<int>& vars, const std::vector<bool>& G) {
    for (int x : vars) if (!G[x]) return false;
    return true;
}

std::optional<std::vector<bool>> llpHornSAT(
        const std::vector<std::vector<int>>& body,
        const std::vector<int>& head, int n) {
    std::vector<bool> G(n, false);
    bool changed = true;
    while (changed) {
        changed = false;
        int m = (int)body.size();
        for (int c = 0; c < m; ++c) {
            if (!allTrue(body[c], G)) continue;
            if (head[c] == -1) return std::nullopt;
            if (!G[head[c]]) { G[head[c]] = true; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> body = { {}, {0}, {0, 1} };
    std::vector<int> head = { 0, 1, 2 };
    auto G = llpHornSAT(body, head, /*n=*/3);
    std::cout << "G:";
    for (bool b : *G) std::cout << ' ' << (b ? 1 : 0);
    std::cout << '\n';

    // Unsatisfiable: x0 forced true, then x0 => false.
    std::vector<std::vector<int>> bodyU = { {}, {0} };
    std::vector<int> headU = { 0, -1 };
    auto GU = llpHornSAT(bodyU, headU, 1);
    std::cout << "UNSAT case: " << (GU.has_value() ? "sat" : "unsatisfiable") << '\n';
    return 0;
}
