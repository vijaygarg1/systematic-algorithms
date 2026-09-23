"""Classical Prim MST: O(n²) linear-scan version using a weight matrix."""

import math


def mst(w):
    n = len(w)
    d      = [math.inf] * n
    parent = [None] * n
    fixed  = [False] * n
    d[0] = 0
    for _ in range(n):
        v = -1
        best = math.inf
        for k in range(n):
            if not fixed[k] and d[k] < best:
                v = k
                best = d[k]
        if v == -1:
            break
        fixed[v] = True
        for k in range(n):
            if not fixed[k] and w[v][k] != math.inf and w[v][k] < d[k]:
                d[k] = w[v][k]
                parent[k] = v
    return parent


if __name__ == "__main__":
    inf = math.inf
    w = [
        [0, 1, 3, inf, inf],
        [1, 0, 2, 6, inf],
        [3, 2, 0, 4, 5],
        [inf, 6, 4, 0, 7],
        [inf, inf, 5, 7, 0],
    ]
    print("parent:", mst(w))
