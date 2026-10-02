"""LLP FPTAS for Knapsack: discard items that cannot fit, then run a profit-indexed
min-weight DP D[i][p] on a lattice of size (nf+1) x (V'+1), V' = O(nf^2/eps), where
nf is the number of feasible items, driven by a forbidden / advance pair on (i, p)
pairs.  Fully polynomial."""


def llp_approx_knapsack(w, v, W, eps_num, eps_den):
    n = len(w)
    # Discard items with w[i] > W (they can never be in a feasible solution).
    feasible = [i for i in range(n) if w[i] <= W]
    fw = [w[i] for i in feasible]
    fv = [v[i] for i in feasible]
    nf = len(feasible)
    # M = max profit among feasible items (this item fits, so M <= OPT).
    M = max(fv) if nf > 0 else 1
    v_prime = [(fv[k] * nf * eps_den) // (eps_num * M) for k in range(nf)]
    Vp = sum(v_prime)
    INF = 1 + sum(fw)  # a weight above any achievable total weight
    # D[i][p] = min weight of a subset of the first i feasible items with scaled profit >= p.
    D = [[INF] * (Vp + 1) for _ in range(nf + 1)]
    for i in range(nf + 1):
        D[i][0] = 0
    # Forbidden/advance fixpoint: lower D[i][p] to min(skip, take).
    changed = True
    while changed:
        changed = False
        for i in range(1, nf + 1):
            for p in range(1, Vp + 1):
                target = D[i - 1][p]
                prev = max(0, p - v_prime[i - 1])
                take = fw[i - 1] + D[i - 1][prev]
                if take < target:
                    target = take
                if target < D[i][p]:
                    D[i][p] = target
                    changed = True
    return D


if __name__ == "__main__":
    w = [2, 3, 4]
    v = [30, 40, 50]
    W = 6
    D = llp_approx_knapsack(w, v, W, eps_num=1, eps_den=5)
    nf = len(D) - 1
    Vp = len(D[nf]) - 1
    p_star = max((p for p in range(Vp + 1) if D[nf][p] <= W), default=0)
    mu = (1 / 5) * max(v) / len(w)
    print('best scaled profit p* =', p_star, ' estimated profit =', mu * p_star)
