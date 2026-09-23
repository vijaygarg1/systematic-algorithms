// Edmonds-Karp: Ford-Fulkerson with BFS to pick shortest augmenting paths.

#include <iostream>
#include <vector>
#include <queue>
#include <climits>

using Mat = std::vector<std::vector<int>>;

bool bfsResidual(const Mat& c, const Mat& f, int s, int t,
                 std::vector<int>& parent) {
    int n = (int)c.size();
    std::vector<bool> seen(n, false);
    std::queue<int> q;
    q.push(s);
    seen[s] = true;
    parent[s] = s;
    while (!q.empty()) {
        int u = q.front(); q.pop();
        if (u == t) return true;
        for (int v = 0; v < n; ++v)
            if (!seen[v] && c[u][v] - f[u][v] > 0) {
                seen[v] = true;
                parent[v] = u;
                q.push(v);
            }
    }
    return seen[t];
}

Mat maxflow(const Mat& c, int s, int t) {
    int n = (int)c.size();
    Mat f(n, std::vector<int>(n, 0));
    std::vector<int> parent(n);
    while (bfsResidual(c, f, s, t, parent)) {
        int bottleneck = INT_MAX;
        for (int v = t; v != s; v = parent[v])
            bottleneck = std::min(bottleneck, c[parent[v]][v] - f[parent[v]][v]);
        for (int v = t; v != s; v = parent[v]) {
            f[parent[v]][v] += bottleneck;
            f[v][parent[v]] -= bottleneck;
        }
    }
    return f;
}

int main() {
    int n = 4;
    Mat c(n, std::vector<int>(n, 0));
    c[0][1] = 3; c[0][2] = 2; c[1][2] = 1; c[1][3] = 2; c[2][3] = 3;
    auto f = maxflow(c, 0, 3);
    int total = 0;
    for (int v = 0; v < n; ++v) total += f[0][v];
    std::cout << "max flow s=0 t=3: " << total << '\n';
    return 0;
}
