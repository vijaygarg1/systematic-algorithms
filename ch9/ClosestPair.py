"""Divide-and-conquer closest pair of points (squared distance), matching
bxx-divideConquer.tex Algorithm ClosestPair: base case brute force for
<= 3 points; otherwise recurse on both halves, then combine by scanning
the strip of points within `best` of the dividing line, checking each
strip point only against the next 15 points in y-order.

PRECONDITION -- the book's "presort once" line, extended to BOTH
coordinates so that the combine step never sorts anything: Px is
non-decreasing with Py[k] the y-coordinate of the same point, and Ys
lists the indices 0..n-1 in non-decreasing y-order. Both presorts are
done once by the caller, with the host language's own sort.

Every call receives its range's indices in y-order, splits that order
between the two halves in O(size), and reads the strip off it already
y-sorted, so T(n) = 2T(n/2) + O(n) = O(n log n) -- the classical
refinement named at the end of the book's analysis, in place of the
O(n log^2 n) version that re-sorted the strip at every level.

Returns [best squared distance, one endpoint, the other], naming the
closest PAIR as the book's KwOut does.
"""

import math


def find(lo, hi, Px, Py, Ys):
    G = [math.inf, -1, -1]
    closest_pair(lo, hi, Px, Py, Ys, G)
    return G


def check(i, j, Px, Py, G):
    """Keep the pair (i, j) when it beats the best seen so far."""
    d = (Px[i] - Px[j]) ** 2 + (Py[i] - Py[j]) ** 2
    if d < G[0]:
        G[0], G[1], G[2] = d, i, j


def closest_pair(lo, hi, Px, Py, Ys, G):
    if hi <= lo:
        return
    if hi - lo <= 2:
        for i in range(lo, hi):
            for j in range(i + 1, hi + 1):
                check(i, j, Px, Py, G)
        return
    mid = (lo + hi) // 2
    mid_x = Px[mid]
    # Divide: split the inherited y-order between the two halves, each
    # half keeping its points in y-order.
    Ls = [p for p in Ys if p <= mid]
    Rs = [p for p in Ys if p > mid]
    closest_pair(lo, mid, Px, Py, Ls, G)
    closest_pair(mid + 1, hi, Px, Py, Rs, G)
    # Combine: the same y-order, filtered to the points within
    # sqrt(best) of the dividing line -- already sorted by y.
    strip = [p for p in Ys if (Px[p] - mid_x) ** 2 < G[0]]
    # Each strip point need only be checked against the next 15 points
    # in y-order (Lemma: at most 15 positions apart).
    for a in range(len(strip)):
        for b in range(a + 1, min(a + 16, len(strip))):
            check(strip[a], strip[b], Px, Py, G)


if __name__ == "__main__":
    # The book's worked example (bxx-divideConquer.tex).
    pts = [(1.0, 2.0), (3.0, 7.0), (5.0, 4.0), (8.0, 9.0), (9.0, 6.0), (10.0, 2.0)]
    pts.sort(key=lambda p: p[0])                        # presort by x, once
    Px = [p[0] for p in pts]
    Py = [p[1] for p in pts]
    Ys = sorted(range(len(pts)), key=lambda k: Py[k])   # presort by y, once
    G = find(0, len(Px) - 1, Px, Py, Ys)
    i, j = int(G[1]), int(G[2])
    print(f"closest pair: ({Px[i]}, {Py[i]}) and ({Px[j]}, {Py[j]}), "
          f"squared distance {G[0]} (distance {math.sqrt(G[0]):.3f})")
