// LLP-Dijkstra: priority-queue scheduler over the distance vector.

#include <iostream>
#include <vector>
#include <queue>
#include <climits>

using Matrix = std::vector<std::vector<int>>;
constexpr int INF = INT_MAX / 2;

std::vector<int> llpDijkstra(const std::vector<std::vector<int>>& adj,
                             const Matrix& w, int s) {
    int n = (int)adj.size();
    std::vector<int>  d(n, INF);
    std::vector<bool> fixedFlag(n, false);
    d[s] = 0;
    using P = std::pair<int, int>;          // (dist, vertex)
    std::priority_queue<P, std::vector<P>, std::greater<P>> heap;
    heap.push({0, s});
    while (!heap.empty()) {
        auto [dj, j] = heap.top(); heap.pop();
        if (fixedFlag[j]) continue;
        fixedFlag[j] = true;
        for (int k : adj[j]) {
            int nd = dj + w[j][k];
            if (nd < d[k]) { d[k] = nd; heap.push({nd, k}); }
        }
    }
    return d;
}

int main() {
    int n = 5;
    Matrix w(n, std::vector<int>(n, INF));
    w[0][1] = 4;  w[0][2] = 1;  w[1][2] = 2;  w[1][3] = 5;
    w[2][3] = 8;  w[2][4] = 10; w[3][4] = 2;
    std::vector<std::vector<int>> adj(n);
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
            if (i != j && w[i][j] != INF) adj[i].push_back(j);
    auto d = llpDijkstra(adj, w, 0);
    std::cout << "d from 0:";
    for (int x : d) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
