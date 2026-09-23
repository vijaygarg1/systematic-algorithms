// Ford-Fulkerson max-flow via DFS-found augmenting paths.

#include <iostream>
#include <vector>
#include <climits>

using Mat = std::vector<std::vector<int>>;

int residual(const Mat& c, const Mat& f, int u, int v) {
    return c[u][v] - f[u][v];
}

bool augmentingPath(const Mat& c, const Mat& f, int s, int t,
                    std::vector<int>& parent) {
    int n = (int)c.size();
    std::vector<bool> seen(n, false);
    std::vector<int> stk = {s};
    seen[s] = true; parent[s] = s;
    while (!stk.empty()) {
        int u = stk.back(); stk.pop_back();
        if (u == t) return true;
        for (int v = 0; v < n; ++v)
            if (!seen[v] && residual(c, f, u, v) > 0) {
                seen[v] = true;
                parent[v] = u;
                stk.push_back(v);
            }
    }
    return false;
}

Mat maxflow(const Mat& c, int s, int t) {
    int n = (int)c.size();
    Mat f(n, std::vector<int>(n, 0));
    std::vector<int> parent(n);
    while (augmentingPath(c, f, s, t, parent)) {
        int bottleneck = INT_MAX;
        for (int v = t; v != s; v = parent[v])
            bottleneck = std::min(bottleneck, residual(c, f, parent[v], v));
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
