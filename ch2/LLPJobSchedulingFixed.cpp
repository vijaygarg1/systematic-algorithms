// LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed.

#include <iostream>
#include <vector>

std::vector<int> jobSchedulingFixed(
        const std::vector<int>& t,
        const std::vector<std::vector<int>>& pre) {
    int n = (int)t.size();
    std::vector<int> G = t;
    std::vector<bool> fixedv(n);
    for (int k = 0; k < n; ++k) fixedv[k] = pre[k].empty();

    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (fixedv[j]) continue;
            bool ready = true;
            for (int i : pre[j]) if (!fixedv[i]) { ready = false; break; }
            if (!ready) continue;
            int best = G[pre[j][0]] + t[j];
            for (int i : pre[j]) {
                int v = G[i] + t[j];
                if (v > best) best = v;
            }
            G[j] = best;
            fixedv[j] = true;
            changed = true;
        }
    }
    return G;
}

int main() {
    std::vector<int>              t   = {3, 2, 5, 1};
    std::vector<std::vector<int>> pre = {{}, {0}, {0}, {1, 2}};
    auto G = jobSchedulingFixed(t, pre);
    std::cout << "G =";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
