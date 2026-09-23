// Generic queue-based reachability from vertex 0.

#include <iostream>
#include <vector>
#include <queue>

std::vector<int> traversal(const std::vector<std::vector<int>>& dep) {
    int n = (int)dep.size();
    std::vector<int> G(n, 0);
    G[0] = 1;
    std::queue<int> q;
    q.push(0);
    while (!q.empty()) {
        int j = q.front(); q.pop();
        for (int k : dep[j]) {
            if (G[k] == 0) { G[k] = 1; q.push(k); }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> dep = {
        {1, 2}, {3}, {3, 4}, {5}, {5}, {}, {7}, {}
    };
    auto G = traversal(dep);
    std::cout << "reachable:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
