// Floyd-Warshall APSP: triple loop on intermediate vertex.

#include <iostream>
#include <vector>
#include <climits>

constexpr int INF = INT_MAX / 2;

void floydWarshall(std::vector<std::vector<int>>& G) {
    int n = (int)G.size();
    for (int k = 0; k < n; ++k)
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                if (G[i][k] < INF && G[k][j] < INF && G[i][k] + G[k][j] < G[i][j])
                    G[i][j] = G[i][k] + G[k][j];
}

int main() {
    int n = 4;
    std::vector<std::vector<int>> G(n, std::vector<int>(n, INF));
    for (int i = 0; i < n; ++i) G[i][i] = 0;
    G[0][1] = 5; G[0][3] = 10; G[1][2] = 3; G[2][3] = 1;
    floydWarshall(G);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) std::cout << (G[i][j] >= INF ? -1 : G[i][j]) << ' ';
        std::cout << '\n';
    }
    return 0;
}
