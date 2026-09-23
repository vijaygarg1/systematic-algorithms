"""Classical Dijkstra: extract-min frontier vertex, relax outgoing edges."""

INF = 2 ** 31 - 1


def shortest_path(w, s):
    n = len(w)
    dist = [INF] * n
    fixed = [False] * n
    dist[s] = 0
    for _ in range(n):
        j, best = -1, INF
        for k in range(n):
            if not fixed[k] and dist[k] < best:
                j, best = k, dist[k]
        if j == -1:
            return dist
        fixed[j] = True
        for k in range(n):
            if not fixed[k] and w[j][k] < INF:
                if dist[j] + w[j][k] < dist[k]:
                    dist[k] = dist[j] + w[j][k]
    return dist


if __name__ == "__main__":
    # 5 vertices; INF marks no edge.
    w = [
        [0,   4,   1,   INF, INF],
        [INF, 0,   INF, 1,   INF],
        [INF, 2,   0,   5,   INF],
        [INF, INF, INF, 0,   3],
        [INF, INF, INF, INF, 0],
    ]
    print(shortest_path(w, 0))
