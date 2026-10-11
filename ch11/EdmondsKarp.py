"""Edmonds-Karp max-flow: Ford-Fulkerson with BFS (shortest augmenting
paths) in place of DFS. Shares its outer loop verbatim with
FordFulkerson.py -- the two differ only in find_augmenting_path."""


def residual(c, f, u, v):
    return c[u][v] - f[u][v]


def bfs(c, f, t, parent, seen, q, head):
    if head >= len(q):
        return False
    u = q[head]
    if u == t:
        return True
    n = len(c)
    for v in range(n):
        if not seen[v] and residual(c, f, u, v) > 0:
            seen[v] = True
            parent[v] = u
            q.append(v)
    return bfs(c, f, t, parent, seen, q, head + 1)


def find_augmenting_path(c, f, s, t, parent):
    n = len(c)
    seen = [False] * n
    seen[s] = True
    parent[s] = s
    return bfs(c, f, t, parent, seen, [s], 0)


def maxflow(c, s, t):
    n = len(c)
    f = [[0] * n for _ in range(n)]
    parent = [0] * n
    while find_augmenting_path(c, f, s, t, parent):
        bottleneck = float("inf")
        v = t
        while v != s:
            u = parent[v]
            bottleneck = min(bottleneck, residual(c, f, u, v))
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
