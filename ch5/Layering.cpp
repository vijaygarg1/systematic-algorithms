// Layering of a DAG via Kahn's algorithm.

#include <iostream>
#include <vector>
#include <queue>

std::vector<int> layering(const std::vector<std::vector<int>>& pre,
                          const std::vector<std::vector<int>>& succ) {
    int n = (int)pre.size();
    std::vector<int> G(n, 0);
    std::vector<int> indeg(n);
    for (int j = 0; j < n; ++j) indeg[j] = (int)pre[j].size();
    std::queue<int> q;
    for (int j = 0; j < n; ++j) if (indeg[j] == 0) q.push(j);
    while (!q.empty()) {
        int j = q.front(); q.pop();
        for (int k : succ[j]) {
            if (--indeg[k] == 0) {
                int best = 0;
                for (int i : pre[k]) if (G[i] + 1 > best) best = G[i] + 1;
                G[k] = best;
                q.push(k);
            }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> pre  = {{}, {0}, {0}, {1, 2}, {2}, {3, 4}};
    std::vector<std::vector<int>> succ = {{1, 2}, {3}, {3, 4}, {5}, {5}, {}};
    auto G = layering(pre, succ);
    std::cout << "layers:"; for (int x : G) std::cout << ' ' << x; std::cout << '\n';
    return 0;
}
