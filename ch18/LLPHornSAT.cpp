// LLP Horn SAT: forbidden when an implication's antecedents are all
// true but the consequent x_j is false; advance sets x_j to true.

#include <iostream>
#include <vector>

bool hornImplied(int j, const std::vector<bool>& G,
                 const std::vector<std::vector<int>>& body,
                 const std::vector<int>& head) {
    if (G[j]) return false;
    int m = (int)body.size();
    for (int c = 0; c < m; ++c) {
        if (head[c] != j) continue;
        bool allTrue = true;
        for (int x : body[c]) if (!G[x]) { allTrue = false; break; }
        if (allTrue) return true;
    }
    return false;
}

std::vector<bool> llpHornSAT(const std::vector<std::vector<int>>& body,
                             const std::vector<int>& head, int n) {
    std::vector<bool> G(n, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j)
            if (hornImplied(j, G, body, head)) { G[j] = true; changed = true; }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> body = { {}, {0}, {0, 1} };
    std::vector<int> head = { 0, 1, 2 };
    auto G = llpHornSAT(body, head, /*n=*/3);
    std::cout << "G:";
    for (bool b : G) std::cout << ' ' << (b ? 1 : 0);
    std::cout << '\n';
    return 0;
}
