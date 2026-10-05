"""Divide-and-conquer closest-pair-of-points (squared distance): base
case brute force for <=3 points; otherwise recurse on both halves, then
combine by collecting the strip of points within `best` of the dividing
line, sorting the strip BY Y-COORDINATE, and checking each strip point
only against the next 15 points in that y-order -- giving the book's
O(n log^2 n) bound (re-sorting the strip at every level), not an
O((hi-lo)^2) brute-force scan over every pair in range."""

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
    mid_x = Px[mid]
    closest_pair(lo, mid, Px, Py, G)
    closest_pair(mid + 1, hi, Px, Py, G)
    # Combine: gather the strip -- points within sqrt(best) of the
    # dividing line -- then sort it by y-coordinate.
    strip = [k for k in range(lo, hi + 1) if (Px[k] - mid_x) ** 2 < G[0]]
    strip.sort(key=lambda k: Py[k])
    # Each strip point need only be checked against the next 15 points
    # in y-order (Lemma: at most 15 positions apart).
    for a in range(len(strip)):
        for b in range(a + 1, min(a + 16, len(strip))):
            pi, pj = strip[a], strip[b]
            d = (Px[pi] - Px[pj]) ** 2 + (Py[pi] - Py[pj]) ** 2
            if d < G[0]:
                G[0] = d


if __name__ == "__main__":
    Px = [0.0, 1.0, 3.0, 4.0, 7.0]
    Py = [0.0, 5.0, 2.0, 1.0, 6.0]
    G = find(0, len(Px) - 1, Px, Py)
    print(f"min squared distance: {G[0]} (distance {G[0] ** 0.5:.3f})")
