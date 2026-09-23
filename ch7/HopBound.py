"""HopBound: composition program enforcing h[j] <= k.  Returns None on
infeasibility."""


def hop_bound(h, k, G):
    return None if any(hi > k for hi in h) else G


if __name__ == "__main__":
    h = [0, 1, 2, 3]
    G = [0, 4, 9, 15]
    print(hop_bound(h, 2, G))
