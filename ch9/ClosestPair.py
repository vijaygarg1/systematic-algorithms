"""Divide-and-conquer closest-pair-of-points (squared distance)."""

import math


def find(lo, hi, Px, Py):
    G = [math.inf]
    closest_pair(lo, hi, Px, Py, G)
    return G


def closest_pair(lo, hi, Px, Py, G):
    if hi <= lo:
        return
    if hi - lo <= 2:
        for i in range(lo, hi):
            for j in range(i + 1, hi + 1):
                d = (Px[i] - Px[j]) ** 2 + (Py[i] - Py[j]) ** 2
                if d < G[0]:
                    G[0] = d
        return
    mid = (lo + hi) // 2
    closest_pair(lo, mid, Px, Py, G)
    closest_pair(mid + 1, hi, Px, Py, G)
    # Combine: scan the strip near x = Px[mid].
    for i in range(lo, hi):
        for j in range(i + 1, hi + 1):
            if ((Px[i] - Px[mid]) ** 2 < G[0]
                    and (Px[j] - Px[mid]) ** 2 < G[0]):
                d = (Px[i] - Px[j]) ** 2 + (Py[i] - Py[j]) ** 2
                if d < G[0]:
                    G[0] = d


if __name__ == "__main__":
    Px = [0.0, 1.0, 3.0, 4.0, 7.0]
    Py = [0.0, 5.0, 2.0, 1.0, 6.0]
    G = find(0, len(Px) - 1, Px, Py)
    print(f"min squared distance: {G[0]} (distance {G[0] ** 0.5:.3f})")
