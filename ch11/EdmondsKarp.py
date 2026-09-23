"""Edmonds-Karp: Ford-Fulkerson with BFS to pick shortest augmenting paths."""

from collections import deque


def bfs_residual(c, f, s, t, parent):
    n = len(c)
    seen = [False] * n
    q = deque([s])
    seen[s] = True
    parent[s] = s
    while q:
        u = q.popleft()
        if u == t:
            return True
        for v in range(n):
            if not seen[v] and c[u][v] - f[u][v] > 0:
                seen[v] = True
                parent[v] = u
                q.append(v)
    return seen[t]


def maxflow(c, s, t):
    n = len(c)
    f = [[0] * n for _ in range(n)]
    parent = [0] * n
    while bfs_residual(c, f, s, t, parent):
        bottleneck = float("inf")
        v = t
        while v != s:
            u = parent[v]
            bottleneck = min(bottleneck, c[u][v] - f[u][v])
            v = u
        v = t
        while v != s:
            u = parent[v]
            f[u][v] += bottleneck
            f[v][u] -= bottleneck
            v = u
    return f


if __name__ == "__main__":
    c = [[0]*6 for _ in range(6)]
    for u, v, cap in [(0,1,16),(0,3,13),(1,2,12),(1,3,4),
                      (3,4,14),(2,5,9),(2,4,4),(4,5,20)]:
        c[u][v] = cap
    f = maxflow(c, 0, 5)
    print(f"max flow value: {sum(f[0][v] for v in range(6))}")
