"""MandatoryEdges: composition program forcing the edges in subset M
into the spanning tree.  Returns None when a mandatory edge closes a
cycle with previously committed mandatory edges."""


def _find(parent, x):
    while parent[x] != x:
        parent[x] = parent[parent[x]]
        x = parent[x]
    return x


def _union(parent, a, b):
    ra, rb = _find(parent, a), _find(parent, b)
    if ra != rb:
        parent[ra] = rb


def mandatory_edges(u, v, M, n):
    m = len(u)
    parent = list(range(n))
    G = [False] * m
    changed = True
    while changed:
        changed = False
        for j in range(m):
            if M[j] and not G[j]:
                if _find(parent, u[j]) == _find(parent, v[j]):
                    return None
                G[j] = True
                _union(parent, u[j], v[j])
                changed = True
    return G


if __name__ == "__main__":
    u = [0, 1, 0]
    v = [1, 2, 2]
    M = [True, True, True]   # all three mandatory => cycle
    print(mandatory_edges(u, v, M, 3))
