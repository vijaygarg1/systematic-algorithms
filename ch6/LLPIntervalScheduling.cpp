// LLP-IntervalScheduling: G[j] = true when job j is selected; forbidden
// when j is unselected and compatible with every already-selected
// earlier job (assumes jobs sorted by finish time).

#include <iostream>
#include <vector>

std::vector<bool> llpIntervalScheduling(const std::vector<int>& s,
                                        const std::vector<int>& f) {
    int n = (int)s.size();
    std::vector<bool> G(n, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (G[j]) continue;
            bool compat = true;
            for (int i = 0; i < j && compat; ++i)
                if (G[i] && f[i] > s[j]) compat = false;
            if (compat) { G[j] = true; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> s = {1, 3, 0, 5, 8, 5};
    std::vector<int> f = {2, 4, 6, 7, 9, 9};
    auto G = llpIntervalScheduling(s, f);
    std::cout << "selected:";
    for (size_t j = 0; j < G.size(); ++j) if (G[j]) std::cout << ' ' << j;
    std::cout << '\n';
    return 0;
}
