"""FPTAS for Knapsack: scale values down by scale = eps*M/n, then run the standard 0/1 DP on the scaled instance."""


def fptas_knapsack(w, v, W, eps_num, eps_den):
    n = len(w)
    M = max(v)
    # scale = (eps_num / eps_den) * M / n; v'[i] = floor(v[i] / scale)
    v_prime = [(v[i] * n * eps_den) // (eps_num * M) for i in range(n)]
    dp = [[0] * (W + 1) for _ in range(n + 1)]
    for i in range(1, n + 1):
        for c in range(W + 1):
            dp[i][c] = dp[i - 1][c]
            if w[i - 1] <= c:
                take = dp[i - 1][c - w[i - 1]] + v_prime[i - 1]
                if take > dp[i][c]:
                    dp[i][c] = take
    # Backtrack to recover the chosen items.
    S = [False] * n
    rem = W
    for i in range(n, 0, -1):
        if dp[i][rem] != dp[i - 1][rem]:
            S[i - 1] = True
            rem -= w[i - 1]
    return S


if __name__ == "__main__":
    # 3 items, capacity 6, eps = 1/5 = 0.2.
    w = [2, 3, 4]
    v = [30, 40, 50]
    W = 6
    print(fptas_knapsack(w, v, W, eps_num=1, eps_den=5))
