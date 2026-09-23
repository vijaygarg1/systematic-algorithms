"""LLP FPTAS for Knapsack: scaled-value DP on the 2D lattice G[i, c] driven by a forbidden / advance pair on (i, c) pairs."""


def llp_approx_knapsack(w, v, W, eps_num, eps_den):
    n = len(w)
    M = max(v)
    v_prime = [(v[i] * n * eps_den) // (eps_num * M) for i in range(n)]
    G = [[0] * (W + 1) for _ in range(n + 1)]
    changed = True
    while changed:
        changed = False
        for i in range(1, n + 1):
            for c in range(W + 1):
                target = G[i - 1][c]
                if w[i - 1] <= c:
                    take = G[i - 1][c - w[i - 1]] + v_prime[i - 1]
                    if take > target:
                        target = take
                if G[i][c] < target:
                    G[i][c] = target
                    changed = True
    return G


if __name__ == "__main__":
    w = [2, 3, 4]
    v = [30, 40, 50]
    W = 6
    table = llp_approx_knapsack(w, v, W, eps_num=1, eps_den=5)
    print('best scaled value =', table[len(w)][W])
