"""Ford-Fulkerson max-flow via DFS-found augmenting paths."""

def augmenting_path(c, f, s, t, parent):
    n = len(c)
    seen = [False] * n
    stack = [s]
    seen[s] = True
    parent[s] = s
    while stack:
        u = stack.pop()
        if u == t:
            return True
        for v in range(n):
            if not seen[v] and c[u][v] - f[u][v] > 0:
                seen[v] = True
                parent[v] = u
                stack.append(v)
    return False


def maxflow(c, s, t):
    n = len(c)
    f = [[0] * n for _ in range(n)]
    parent = [0] * n
    while augmenting_path(c, f, s, t, parent):
        # Bottleneck residual capacity along the path.
        bottleneck = float("inf")
        v = t
        while v != s:
            u = parent[v]
            bottleneck = min(bottleneck, c[u][v] - f[u][v])
            v = u
        # Push bottleneck units along the path.
        v = t
        while v != s:
            u = parent[v]
            f[u][v] += bottleneck
            f[v][u] -= bottleneck
            v = u
    return f


if __name__ == "__main__":
    # Book's 6-vertex flow network (s=0, a=1, b=2, c=3, d=4, t=5).
    c = [[0]*6 for _ in range(6)]
    edges = [(0,1,16),(0,3,13),(1,2,12),(1,3,4),(3,4,14),(2,5,9),(2,4,4),(4,5,20)]
    for u, v, cap in edges:
        c[u][v] = cap
    f = maxflow(c, 0, 5)
    total = sum(f[0][v] for v in range(6))
    print(f"max flow value: {total}")
