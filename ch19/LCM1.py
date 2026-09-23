"""LCM1: ascending LLP to find least common multiple."""

import math


def lcm1(A):
    n = len(A)
    G = list(A)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            for i in range(n):
                if G[j] < G[i]:
                    diff = G[i] - G[j]
                    steps = math.ceil(diff / A[j])
                    G[j] += steps * A[j]
                    changed = True
    return G[0]


if __name__ == "__main__":
    for A in [[4, 6], [4, 6, 10], [12, 18, 24]]:
        print(f"lcm1({A}) = {lcm1(A)}")
