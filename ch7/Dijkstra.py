"""Classical Dijkstra: binary min-heap H of (cost, vertex) pairs, with
lazy deletion -- a vertex may be pushed more than once as its distance
improves; stale entries are skipped on removal via the fixed[] check."""

import heapq

INF = 2 ** 31 - 1


def shortest_path(w, s):
    n = len(w)
    dist = [INF] * n
    fixed = [False] * n
    dist[s] = 0
    heap = [(0, s)]
    while heap:
        c, j = heapq.heappop(heap)
        if fixed[j]:
            continue
        fixed[j] = True
        for k in range(n):
            if not fixed[k] and w[j][k] < INF and dist[j] + w[j][k] < dist[k]:
                dist[k] = dist[j] + w[j][k]
                heapq.heappush(heap, (dist[k], k))
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
