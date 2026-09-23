"""Classical Bellman-Ford: n-1 relaxation passes over every edge."""

INF = 2 ** 31 - 1


def shortest_path(n, U, V, W, s):
    dist = [INF] * n
    dist[s] = 0
    for _ in range(n - 1):
        for e in range(len(U)):
            u, v = U[e], V[e]
            if dist[u] != INF and dist[u] + W[e] < dist[v]:
                dist[v] = dist[u] + W[e]
    return dist


if __name__ == "__main__":
    # 5-vertex graph with one negative edge.
    n = 5
    U = [0, 0, 1, 2, 3]
    V = [1, 2, 3, 1, 4]
    W = [4, 1, 1, -3, 3]
    print(shortest_path(n, U, V, W, 0))
