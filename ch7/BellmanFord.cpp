// Classical Bellman-Ford: n-1 relaxation passes over every edge.

#include <iostream>
#include <vector>
#include <climits>

constexpr int INF = INT_MAX / 2;

std::vector<int> shortestPath(int n,
                              const std::vector<int>& U,
                              const std::vector<int>& V,
                              const std::vector<int>& W,
                              int s) {
    std::vector<int> dist(n, INF);
    dist[s] = 0;
    int m = (int)U.size();
    for (int k = 1; k < n; ++k) {
        for (int e = 0; e < m; ++e) {
            int u = U[e], v = V[e];
            if (dist[u] != INF && dist[u] + W[e] < dist[v])
                dist[v] = dist[u] + W[e];
        }
    }
    return dist;
}

int main() {
    int n = 5;
    std::vector<int> U = {0, 0, 1, 2, 3};
    std::vector<int> V = {1, 2, 3, 3, 4};
    std::vector<int> Wt = {4, 1, 5, 2, 3};
    auto d = shortestPath(n, U, V, Wt, 0);
    std::cout << "dist:";
    for (int x : d) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
