// LLP-JobScheduling-Forbidden: minimum completion time with prerequisites.
//   forbidden(j) : G[j] < max { G[i] + t[j] | i in pre[j] }
//   advance(j)   : G[j] := max { G[i] + t[j] | i in pre[j] }

#include <iostream>
#include <vector>
#include <algorithm>

bool forbidden(int j, const std::vector<int>& G,
               const std::vector<int>& t,
               const std::vector<std::vector<int>>& pre) {
    if (pre[j].empty()) return false;
    int rhs = 0;
    bool first = true;
    for (int i : pre[j]) {
        int v = G[i] + t[j];
        if (first || v > rhs) { rhs = v; first = false; }
    }
    return G[j] < rhs;
}

void advance(int j, std::vector<int>& G,
             const std::vector<int>& t,
             const std::vector<std::vector<int>>& pre) {
    int rhs = 0;
    bool first = true;
    for (int i : pre[j]) {
        int v = G[i] + t[j];
        if (first || v > rhs) { rhs = v; first = false; }
    }
    G[j] = rhs;
}

std::vector<int> jobSchedulingForbidden(const std::vector<int>& t,
                                        const std::vector<std::vector<int>>& pre) {
    int n = (int)t.size();
    std::vector<int> G = t;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (forbidden(j, G, t, pre)) { advance(j, G, t, pre); changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int>              t   = {3, 2, 4, 1, 2, 3};
    std::vector<std::vector<int>> pre = {{}, {0}, {0}, {1, 2}, {2}, {3, 4}};
    auto G = jobSchedulingForbidden(t, pre);
    std::cout << "G =";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
