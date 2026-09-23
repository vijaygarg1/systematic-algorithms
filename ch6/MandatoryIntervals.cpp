// Mandatory: composition program forcing the intervals in subset S
// into the schedule.  Returns an empty vector to signal infeasibility.

#include <iostream>
#include <vector>

static bool overlap(int j, int k,
                    const std::vector<int>& s, const std::vector<int>& f) {
    return s[j] < f[k] && s[k] < f[j];
}

// Returns G; on infeasibility returns an empty vector (different from a
// feasible vector of all-false because at least one G[k] would have to be
// set true on a non-empty S).
std::vector<bool> mandatoryIntervals(const std::vector<int>& s,
                                     const std::vector<int>& f,
                                     const std::vector<bool>& S) {
    int n = (int)s.size();
    std::vector<bool> G(n, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (S[j] && !G[j]) {
                for (int k = 0; k < n; ++k) {
                    if (G[k] && overlap(j, k, s, f)) return {};
                }
                G[j] = true;
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<int> s = {0, 3, 5};
    std::vector<int> f = {2, 7, 9};
    std::vector<bool> S = {true, false, true};
    auto G = mandatoryIntervals(s, f, S);
    if (G.empty()) { std::cout << "infeasible\n"; return 0; }
    std::cout << "G:";
    for (size_t j = 0; j < G.size(); ++j) std::cout << ' ' << (int)G[j];
    std::cout << '\n';
    return 0;
}
