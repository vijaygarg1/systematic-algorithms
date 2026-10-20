"""0/1 knapsack: O(nW) bottom-up table fill."""

def solve(w, v, W):
    n = len(w) - 1
    G = [[0] * (W + 1) for _ in range(n + 1)]
    for i in range(1, n + 1):
        for c in range(1, W + 1):
            if w[i] > c:
                G[i][c] = G[i - 1][c]
            else:
                skip = G[i - 1][c]
                take = G[i - 1][c - w[i]] + v[i]
                G[i][c] = max(skip, take)
    return G


if __name__ == "__main__":
    # Slot 0 is padding; items 1..4.
    w = [0, 2, 3, 4, 5]
    v = [0, 3, 4, 5, 6]
    W = 8
    G = solve(w, v, W)
    print(f"optimum = G[{len(w)-1}][{W}] = {G[len(w)-1][W]}")
