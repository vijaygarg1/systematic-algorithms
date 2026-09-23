// Classical Dijkstra: extract-min frontier vertex, relax outgoing edges.

#include <iostream>
#include <vector>
#include <climits>

constexpr int INF = INT_MAX / 2;

std::vector<int> shortestPath(const std::vector<std::vector<int>>& w, int s) {
    int n = (int)w.size();
    std::vector<int>  dist(n, INF);
    std::vector<bool> fixed(n, false);
    dist[s] = 0;
    for (int count = 0; count < n; ++count) {
        int j = -1, best = INF;
        for (int k = 0; k < n; ++k)
            if (!fixed[k] && dist[k] < best) { j = k; best = dist[k]; }
        if (j == -1) break;
        fixed[j] = true;
        for (int k = 0; k < n; ++k) {
            if (fixed[k] || w[j][k] >= INF) continue;
            if (dist[j] + w[j][k] < dist[k]) dist[k] = dist[j] + w[j][k];
        }
    }
    return dist;
}

int main() {
    int n = 5;
    std::vector<std::vector<int>> w(n, std::vector<int>(n, INF));
    w[0][1] = 4;  w[0][2] = 1;  w[1][2] = 2;  w[1][3] = 5;
    w[2][3] = 8;  w[2][4] = 10; w[3][4] = 2;
    auto d = shortestPath(w, 0);
    std::cout << "dist:";
    for (int x : d) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
