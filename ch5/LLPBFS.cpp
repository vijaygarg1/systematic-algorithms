// LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1.

#include <iostream>
#include <vector>
#include <climits>

bool forbidden(int j, const std::vector<int>& G,
               const std::vector<std::vector<int>>& pre) {
    if (pre[j].empty()) return false;
    int best = INT_MAX;
    for (int i : pre[j]) if (G[i] != INT_MAX && G[i] + 1 < best) best = G[i] + 1;
    return G[j] > best;
}

void advance(int j, std::vector<int>& G,
             const std::vector<std::vector<int>>& pre) {
    int best = INT_MAX;
    for (int i : pre[j]) if (G[i] != INT_MAX && G[i] + 1 < best) best = G[i] + 1;
    G[j] = best;
}

void llpBFS(const std::vector<std::vector<int>>& pre, std::vector<int>& G) {
    int n = (int)G.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (forbidden(j, G, pre)) { advance(j, G, pre); changed = true; }
        }
    }
}

int main() {
    std::vector<std::vector<int>> pre = { {}, {0}, {0}, {1, 2}, {2}, {3, 4} };
    int n = (int)pre.size();
    std::vector<int> G(n, INT_MAX);
    G[0] = 0;
    llpBFS(pre, G);
    std::cout << "BFS distances:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
