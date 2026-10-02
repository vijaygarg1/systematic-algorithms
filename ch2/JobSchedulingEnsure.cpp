// LLP-JobScheduling-Ensure: identical to the Forbidden form, written
// using the `ensure` shorthand.

#include <iostream>
#include <vector>

std::vector<int> jobSchedulingEnsure(const std::vector<int>& t,
                                     const std::vector<std::vector<int>>& pre) {
    int n = (int)t.size();
    std::vector<int> G = t;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (pre[j].empty()) continue;
            int rhs = 0;
            bool first = true;
            for (int i : pre[j]) {
                int v = G[i] + t[j];
                if (first || v > rhs) { rhs = v; first = false; }
            }
            if (G[j] < rhs) { G[j] = rhs; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int>              t   = {3, 2, 4, 1, 2, 3};
    std::vector<std::vector<int>> pre = {{}, {0}, {0}, {1, 2}, {2}, {3, 4}};
    auto G = jobSchedulingEnsure(t, pre);
    std::cout << "G =";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
