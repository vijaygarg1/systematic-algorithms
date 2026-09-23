"""LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each fraction G[j] in [0,1] to its target G*[j] derived from prefix-sum of weights vs capacity W."""


def prefix_weight(upto, w):
    return sum(w[: upto + 1])


def target(j, w, W):
    """G*[j]: 1 while cumulative weight stays under W, the partial fraction
    at the breakpoint, then 0 thereafter."""
    prev = prefix_weight(j - 1, w) if j > 0 else 0.0
    cur = prev + w[j]
    if cur <= W:
        return 1.0
    if prev >= W:
        return 0.0
    return (W - prev) / w[j]


def llp_fractional_knapsack(v, w, W):
    n = len(v)
    G = [0.0] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            t = target(j, w, W)
            if G[j] < t:
                G[j] = t
                changed = True
    return G


if __name__ == "__main__":
    # Items already sorted by v/w density (descending).
    v = [60.0, 100.0, 120.0]
    w = [10.0, 20.0, 30.0]
    W = 50.0
    print(llp_fractional_knapsack(v, w, W))
