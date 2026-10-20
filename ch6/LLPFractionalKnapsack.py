"""LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each fraction G[j] in [0,1] to its target G*[j], read in O(1) from one prefix scan of the weights."""


def prefix_scan(w):
    """One left-to-right scan: S[j] = w[0] + ... + w[j]."""
    S = [0.0] * len(w)
    run = 0.0
    for i in range(len(w)):
        run += w[i]
        S[i] = run
    return S


def target(j, w, W, S):
    """G*[j], read in O(1) from S: 1 while the cumulative weight S[j] stays
    under W, the partial fraction at the breakpoint, then 0 thereafter."""
    prev = S[j - 1] if j > 0 else 0.0
    if S[j] <= W:
        return 1.0
    if prev >= W:
        return 0.0
    return (W - prev) / w[j]


def llp_fractional_knapsack(v, w, W):
    n = len(v)
    G = [0.0] * n
    S = prefix_scan(w)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            t = target(j, w, W, S)
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
