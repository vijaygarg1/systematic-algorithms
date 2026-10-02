"""LLP single-item knapsack step: lift previous-row values to admit one new item."""

def llp_incr_knapsack(w, v, C):
    n = len(C)
    G = [0] * n
    changed = True
    while changed:
        changed = False
        for c in range(n):
            skip = C[c]
            take = C[c - w] + v if c >= w else -1
            best = max(skip, take)
            if G[c] < best:
                G[c] = best
                changed = True
    return G


if __name__ == "__main__":
    # Previous row: best values when only one item (w=2, v=3) was available.
    C = [0, 0, 3, 3, 3, 3, 3, 3, 3]
    # Add a new item (w=3, v=4).
    G = llp_incr_knapsack(3, 4, C)
    print("new row G =", G)
