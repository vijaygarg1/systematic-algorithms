"""Classical Johnson all-pairs shortest paths: BellmanFord computes a
non-negative price vector that reweights edges, then Dijkstra runs from
every source on the reweighted graph. Returns None on negative cycle."""

INF = float("inf")


def dijkstra(w, s, n):
    dist = [INF] * n
    fixed = [False] * n
    dist[s] = 0
    count = 0
    while count < n:
        j = -1
        best = INF
        for k in range(n):
            if not fixed[k] and dist[k] < best:
                j = k
                best = dist[k]
        if j == -1:
            return dist
        fixed[j] = True
        count += 1
        for k in range(n):
            if not fixed[k] and w[j][k] < INF and dist[j] + w[j][k] < dist[k]:
                dist[k] = dist[j] + w[j][k]
    return dist


def all_pairs(w):
    """Return D[u][v] = shortest cost u -> v, or None on negative cycle."""
    n = len(w)
    # Step 1: BellmanFord from auxiliary source. Init dist[i] = 0; n-1 passes.
    dist = [0] * n
    for _ in range(n - 1):
        changed = False
        for i in range(n):
            for j in range(n):
                if w[i][j] < INF and dist[i] + w[i][j] < dist[j]:
                    dist[j] = dist[i] + w[i][j]
                    changed = True
        if not changed:
            break
    # One more pass: negative cycle?
    for i in range(n):
        for j in range(n):
            if w[i][j] < INF and dist[i] + w[i][j] < dist[j]:
                return None
    # Step 2: price[v] = -dist[v].
    price = [-d for d in dist]
    # Step 3: reweight.
    w_prime = [[INF] * n for _ in range(n)]
    for i in range(n):
        for j in range(n):
            if w[i][j] < INF:
                w_prime[i][j] = w[i][j] + price[j] - price[i]
    # Step 4: Dijkstra from every source; undo reweighting.
    D = [[INF] * n for _ in range(n)]
    for u in range(n):
        dist_prime = dijkstra(w_prime, u, n)
        for v in range(n):
            if dist_prime[v] < INF:
                D[u][v] = dist_prime[v] + price[u] - price[v]
    return D


if __name__ == "__main__":
    # 3-vertex directed graph: A->B = 2, B->C = -1, A->C = 4.
    w = [
        [0, 2, 4],
        [INF, 0, -1],
        [INF, INF, 0],
    ]
    D = all_pairs(w)
    if D is None:
        print("negative-weight cycle")
    else:
        for row in D:
            print(" ".join("INF" if x == INF else str(x) for x in row))
