"""LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation."""

import math


def forbidden(j, G, pre, w):
    return any(G[i] != math.inf and G[i] + w[i][j] < G[j] for i in pre[j])


def advance(G, pre, w, n):
    new_G = list(G)
    for k in range(1, n):
        for i in pre[k]:
            if G[i] != math.inf and G[i] + w[i][k] < new_G[k]:
                new_G[k] = G[i] + w[i][k]
    for k in range(1, n):
        G[k] = new_G[k]


def llp_bellman_ford(pre, w, n):
    G = [math.inf] * n
    G[0] = 0
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if forbidden(j, G, pre, w):
                advance(G, pre, w, n)
                changed = True
                break
    return G


if __name__ == "__main__":
    n = 4
    # 0 -> 1 (1), 0 -> 2 (4), 1 -> 2 (-3), 2 -> 3 (1)
    pre = [[], [0], [0, 1], [2]]
    w = [[0, 1, 4, math.inf],
         [math.inf, 0, -3, math.inf],
         [math.inf, math.inf, 0, 1],
         [math.inf, math.inf, math.inf, 0]]
    print("G =", llp_bellman_ford(pre, w, n))
