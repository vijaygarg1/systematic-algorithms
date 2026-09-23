"""LLP-Prim: each non-root vertex advances when the global cross-cut is its lightest edge."""

import math


def min_cross_cut(j, fixed, W, n):
    best = math.inf
    for i in range(n):
        if fixed[i] and W[i][j] < best:
            best = W[i][j]
    return best


def arg_min_cross_cut(j, fixed, W, n):
    best_i = -1
    best = math.inf
    for i in range(n):
        if fixed[i] and W[i][j] < best:
            best = W[i][j]
            best_i = i
    return best_i


def global_min_cross_cut(fixed, W, n):
    best = math.inf
    for j in range(n):
        if not fixed[j]:
            m = min_cross_cut(j, fixed, W, n)
            if m < best:
                best = m
    return best


def propagate_fixed(parent, fixed, n):
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if not fixed[j] and fixed[parent[j]]:
                fixed[j] = True
                changed = True


def llp_prim(parent, fixed, W, C, root):
    n = len(parent)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if fixed[j]:
                continue
            arg = arg_min_cross_cut(j, fixed, W, n)
            if arg < 0:
                continue
            mcc = min_cross_cut(j, fixed, W, n)
            if mcc <= global_min_cross_cut(fixed, W, n) and C[j] < mcc:
                parent[j] = arg
                C[j] = W[arg][j]
                propagate_fixed(parent, fixed, n)
                changed = True
                break
    return C


if __name__ == "__main__":
    inf = math.inf
    W = [
        [0, 1, 3, inf, inf],
        [1, 0, 2, 6, inf],
        [3, 2, 0, 4, 5],
        [inf, 6, 4, 0, 7],
        [inf, inf, 5, 7, 0],
    ]
    n = len(W)
    root = 0
    fixed = [False] * n
    fixed[root] = True
    # parent[j] = argmin over i of W[i][j] for j != root.
    parent = [root] * n
    C = [0.0] * n
    for j in range(n):
        if j == root:
            continue
        best, besti = inf, root
        for i in range(n):
            if i != j and W[i][j] < best:
                best, besti = W[i][j], i
        parent[j] = besti
        C[j] = W[besti][j]
    C = llp_prim(parent, fixed, W, C, root)
    print("parent:", parent)
    print("C:", C)
