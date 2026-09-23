"""LLP-Dijkstra: priority-queue scheduler over the distance vector."""

import math
import heapq


def llp_dijkstra(adj, w, s):
    n = len(adj)
    d = [math.inf] * n
    fixed = [False] * n
    d[s] = 0
    heap = [(0, s)]                       # (d[v], v)
    while heap:
        dj, j = heapq.heappop(heap)
        if fixed[j]:
            continue
        fixed[j] = True                    # advance: fix j
        for k in adj[j]:                   # relax outgoing edges
            nd = dj + w[j][k]
            if nd < d[k]:
                d[k] = nd
                heapq.heappush(heap, (nd, k))
    return d


if __name__ == "__main__":
    # 5-vertex graph (non-negative weights).
    inf = math.inf
    w = [
        [0, 4, 1, inf, inf],
        [inf, 0, 2, 5, inf],
        [inf, inf, 0, 8, 10],
        [inf, inf, inf, 0, 2],
        [inf, inf, inf, inf, 0],
    ]
    adj = [[j for j in range(len(w)) if w[i][j] != inf and i != j]
           for i in range(len(w))]
    print("d from 0:", llp_dijkstra(adj, w, 0))
