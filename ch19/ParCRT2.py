"""Par-CRT2: descending parallel Chinese Remainder Theorem."""

import math


def par_crt2(m, b, M):
    n = len(m)
    G = [0] * n
    for j in range(n):
        r = (M - 1) % m[j]
        if r >= b[j]:
            G[j] = M - 1 - r + b[j]
        else:
            G[j] = M - 1 - r + b[j] - m[j]
    changed = True
    while changed:
        changed = False
        for j in range(n):
            min_val = min(G)
            if G[j] > min_val:
                diff = G[j] - min_val
                steps = math.ceil(diff / m[j])
                G[j] -= steps * m[j]
                changed = True
    return G


if __name__ == "__main__":
    m = [3, 5, 7]
    b = [2, 3, 2]
    M = 3 * 5 * 7
    result = par_crt2(m, b, M)
    print(f"par_crt2(m={m}, b={b}, M={M}) = {result}")
    print(f"solution x = {result[0]}")
