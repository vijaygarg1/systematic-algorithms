// BFS distance from source s.

#include <iostream>
#include <vector>
#include <queue>
#include <climits>

std::vector<int> bfs(const std::vector<std::vector<int>>& dep, int s) {
    int n = (int)dep.size();
    std::vector<int> G(n, INT_MAX);
    G[s] = 0;
    std::queue<int> q;
    q.push(s);
    while (!q.empty()) {
        int j = q.front(); q.pop();
        for (int k : dep[j]) {
            if (G[k] > G[j] + 1) {
                G[k] = G[j] + 1;
                q.push(k);
            }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> dep = {
        {1, 2}, {3}, {3, 4}, {5}, {5}, {}
    };
    auto G = bfs(dep, 0);
    std::cout << "BFS from 0:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
