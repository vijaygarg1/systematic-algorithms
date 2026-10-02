"""FPTAS for Knapsack: discard items that cannot fit, then scale profits by
mu = eps*M/nf (M = max profit among feasible items) and run the standard 0/1 DP
profit-indexed as D[i][p] = min weight to reach scaled profit >= p; the table is
O(nf * V') with V' = O(nf^2/eps), so the scheme is fully polynomial."""


def fptas_knapsack(w, v, W, eps_num, eps_den):
    n = len(w)
    S = [False] * n
    # Discard items with w[i] > W (they can never be in a feasible solution).
    feasible = [i for i in range(n) if w[i] <= W]
    if not feasible:
        return S  # no item fits: empty solution
    fw = [w[i] for i in feasible]
    fv = [v[i] for i in feasible]
    nf = len(feasible)
    # M = max profit among feasible items (this item fits, so M <= OPT).
    M = max(fv)
    # v'[k] = floor(fv[k] / mu), mu = (eps_num/eps_den)*M/nf; V' = sum v'.
    v_prime = [(fv[k] * nf * eps_den) // (eps_num * M) for k in range(nf)]
    Vp = sum(v_prime)
    INF = 1 + sum(fw)  # a weight above any achievable total weight
    # D[i][p] = min weight of a subset of the first i feasible items with scaled profit >= p.
    D = [[INF] * (Vp + 1) for _ in range(nf + 1)]
    for i in range(nf + 1):
        D[i][0] = 0
    for i in range(1, nf + 1):
        for p in range(1, Vp + 1):
            D[i][p] = D[i - 1][p]
            prev = max(0, p - v_prime[i - 1])
            take = fw[i - 1] + D[i - 1][prev]
            if take < D[i][p]:
                D[i][p] = take
    # p* = largest scaled profit achievable within capacity W.
    p_star = 0
    for p in range(Vp + 1):
        if D[nf][p] <= W:
            p_star = p
    # Backtrack over feasible items; map selections back to original indices.
    p = p_star
    for i in range(nf, 0, -1):
        if D[i][p] < D[i - 1][p]:
            S[feasible[i - 1]] = True
            p = max(0, p - v_prime[i - 1])
    return S


if __name__ == "__main__":
    # 3 items, capacity 6, eps = 1/5 = 0.2.
    w = [2, 3, 4]
    v = [30, 40, 50]
    W = 6
    print(fptas_knapsack(w, v, W, eps_num=1, eps_den=5))
    # Reviewer counterexample: capacity 1, the high-profit item is too heavy.
    print(fptas_knapsack([2, 1], [1000, 1], 1, eps_num=1, eps_den=2))
