"""Par-CRT: ascending parallel Chinese Remainder Theorem."""

import math


def par_crt(m, b):
    n = len(m)
    G = list(b)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            for i in range(n):
                if G[j] < G[i]:
                    diff = G[i] - G[j]
                    steps = math.ceil(diff / m[j])
                    G[j] += steps * m[j]
                    changed = True
    return G


if __name__ == "__main__":
    m = [3, 5, 7]
    b = [2, 3, 2]
    result = par_crt(m, b)
    print(f"par_crt(m={m}, b={b}) = {result}")
    print(f"solution x = {result[0]}")
