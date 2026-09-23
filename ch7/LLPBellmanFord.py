"""LLP-BellmanFord: when some d[j] is improvable, run one Jacobi-style relaxation."""

import math


def forbidden(j, d, pre, w):
    return any(d[i] != math.inf and d[i] + w[i][j] < d[j] for i in pre[j])


def advance(d, pre, w, n):
    new_d = list(d)
    for k in range(n):
        for i in pre[k]:
            if d[i] != math.inf and d[i] + w[i][k] < new_d[k]:
                new_d[k] = d[i] + w[i][k]
    for k in range(n):
        d[k] = new_d[k]


def llp_bellman_ford(pre, w, n):
    d = [math.inf] * n
    d[0] = 0
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if forbidden(j, d, pre, w):
                advance(d, pre, w, n)
                changed = True
                break
    return d


if __name__ == "__main__":
    n = 4
    # 0 -> 1 (1), 0 -> 2 (4), 1 -> 2 (-3), 2 -> 3 (1)
    pre = [[], [0], [0, 1], [2]]
    w = [[0, 1, 4, math.inf],
         [math.inf, 0, -3, math.inf],
         [math.inf, math.inf, 0, 1],
         [math.inf, math.inf, math.inf, 0]]
    print("d =", llp_bellman_ford(pre, w, n))
