// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns empty matrix on negative cycle.

#include <iostream>
#include <vector>
#include <climits>

static const int INF = INT_MAX;

std::vector<int> dijkstra(const std::vector<std::vector<int>>& w, int s, int n) {
    std::vector<int> dist(n, INF);
    std::vector<bool> fixed(n, false);
    dist[s] = 0;
    int count = 0;
    while (count < n) {
        int j = -1;
        int best = INF;
        for (int k = 0; k < n; ++k) {
            if (!fixed[k] && dist[k] < best) { j = k; best = dist[k]; }
        }
        if (j == -1) return dist;
        fixed[j] = true;
        ++count;
        for (int k = 0; k < n; ++k) {
            if (!fixed[k] && w[j][k] < INF && dist[j] + w[j][k] < dist[k]) {
                dist[k] = dist[j] + w[j][k];
            }
        }
    }
    return dist;
}

// allPairs returns D such that D[u][v] is the shortest-path cost from u to v;
// returns an empty matrix if a negative-weight cycle exists.
std::vector<std::vector<int>> allPairs(const std::vector<std::vector<int>>& w) {
    int n = (int)w.size();
    // Step 1: BellmanFord from auxiliary source s* with weight-0 edges to
    // every v. Init dist[i] = 0 (the auxiliary edge); n-1 relax passes follow.
    std::vector<int> dist(n, 0);
    for (int k = 0; k < n - 1; ++k) {
        bool changed = false;
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                if (w[i][j] < INF && dist[i] + w[i][j] < dist[j]) {
                    dist[j] = dist[i] + w[i][j];
                    changed = true;
                }
        if (!changed) break;
    }
    // One more pass: any further relaxation indicates a negative cycle.
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
            if (w[i][j] < INF && dist[i] + w[i][j] < dist[j])
                return {};
    // Step 2: price[v] = -dist[v].
    std::vector<int> price(n);
    for (int i = 0; i < n; ++i) price[i] = -dist[i];
    // Step 3: reweight w'[i,j] = w[i,j] + price[j] - price[i].
    std::vector<std::vector<int>> wPrime(n, std::vector<int>(n, INF));
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
            if (w[i][j] < INF) wPrime[i][j] = w[i][j] + price[j] - price[i];
    // Step 4: Dijkstra from every source; undo the reweighting.
    std::vector<std::vector<int>> D(n, std::vector<int>(n, INF));
    for (int u = 0; u < n; ++u) {
        auto distPrime = dijkstra(wPrime, u, n);
        for (int v = 0; v < n; ++v) {
            if (distPrime[v] < INF) D[u][v] = distPrime[v] + price[u] - price[v];
        }
    }
    return D;
}

int main() {
    // 3-vertex directed graph: A->B = 2, B->C = -1, A->C = 4.
    std::vector<std::vector<int>> w = {
        {0, 2, 4},
        {INF, 0, -1},
        {INF, INF, 0}
    };
    auto D = allPairs(w);
    if (D.empty()) {
        std::cout << "negative-weight cycle\n";
    } else {
        for (const auto& row : D) {
            for (int x : row) {
                if (x == INF) std::cout << "INF ";
                else std::cout << x << ' ';
            }
            std::cout << '\n';
        }
    }
    return 0;
}
