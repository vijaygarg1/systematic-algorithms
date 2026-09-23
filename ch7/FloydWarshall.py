"""Floyd-Warshall APSP: triple loop on intermediate vertex."""

INF = 2 ** 31 - 1


def run(G):
    n = len(G)
    for k in range(n):
        for i in range(n):
            for j in range(n):
                if G[i][k] != INF and G[k][j] != INF:
                    if G[i][k] + G[k][j] < G[i][j]:
                        G[i][j] = G[i][k] + G[k][j]


if __name__ == "__main__":
    G = [
        [0,   3,   INF, 7],
        [8,   0,   2,   INF],
        [5,   INF, 0,   1],
        [2,   INF, INF, 0],
    ]
    run(G)
    for row in G:
        print(row)
