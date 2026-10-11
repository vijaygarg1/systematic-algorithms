"""LLP-Knapsack: forbidden(i,c) over the full (i,c) lattice, matching
bxx-dynamicProgramming.tex Algorithm LLP-Knapsack."""

def llp_knapsack(w, v, W):
    n = len(w) - 1
    G = [[0] * (W + 1) for _ in range(n + 1)]
    changed = True
    while changed:
        changed = False
        for i in range(1, n + 1):
            for c in range(1, W + 1):
                if G[i][c] < G[i - 1][c]:
                    G[i][c] = G[i - 1][c]
                    changed = True
                if w[i] <= c and G[i][c] < G[i - 1][c - w[i]] + v[i]:
                    G[i][c] = G[i - 1][c - w[i]] + v[i]
                    changed = True
    return G


if __name__ == "__main__":
    # Slot 0 is padding; items 1..4.
    w = [0, 2, 3, 4, 5]
    v = [0, 3, 4, 5, 6]
    W = 8
    G = llp_knapsack(w, v, W)
    print(f"optimum = G[{len(w)-1}][{W}] = {G[len(w)-1][W]}")
