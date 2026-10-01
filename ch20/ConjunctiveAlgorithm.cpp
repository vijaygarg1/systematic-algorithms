// Conjunctive predicate detection: forbidden when G[j] -> G[i]
// (happened-before); advance increments G[j] to the next local state.

#include <iostream>
#include <vector>

bool happenedBefore(int j, const std::vector<int>& G,
                    const std::vector<std::vector<int>>& vc) {
    int n = (int)G.size();
    int row = j * n + G[j];
    for (int i = 0; i < n; ++i)
        if (i != j && vc[row][i] >= G[i]) return true;
    return false;
}

// Returns the satisfying global state, or an empty vector if none exists
// (some process would need to advance past its last retained state).
std::vector<int> conjunctiveAlgorithm(const std::vector<std::vector<int>>& vc,
                                      const std::vector<int>& T) {
    int n = (int)T.size();
    std::vector<int> G(n, 1);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (happenedBefore(j, G, vc)) {
                if (G[j] >= T[j]) return std::vector<int>();
                ++G[j];
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    // Two processes, each with two events; trivial vector-clock matrix.
    std::vector<std::vector<int>> vc = {
        {0, 0}, {0, 0},
        {0, 0}, {0, 0}
    };
    std::vector<int> T = {2, 2};
    auto G = conjunctiveAlgorithm(vc, T);
    if (G.empty()) {
        std::cout << "no satisfying global state\n";
    } else {
        std::cout << "G:";
        for (int x : G) std::cout << ' ' << x;
        std::cout << '\n';
    }
    return 0;
}
