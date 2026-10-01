"""LLP FPTAS for Knapsack: profit-indexed min-weight DP D[i][p] on a
lattice of size (nf+1) x (V'+1), independent of the capacity W -- this
is what keeps the scheme fully polynomial (a capacity-indexed lattice
would instead grow with W and remain merely pseudo-polynomial).
D[i][p] = minimum total weight of a subset of the first i feasible
items with scaled profit >= p. Returns p* = max{p : D[nf][p] <= W}."""


def llp_approx_knapsack(w, v, W, eps_num, eps_den):
    n = len(w)
    feasible = [i for i in range(n) if w[i] <= W]
    if not feasible:
        return 0
    fw = [w[i] for i in feasible]
    fv = [v[i] for i in feasible]
    nf = len(fw)
    M = max(fv)

    v_prime = [(fv[i] * nf * eps_den) // (eps_num * M) for i in range(nf)]
    Vp = sum(v_prime)

    INF = 1 + sum(fw)
    D = [[INF] * (Vp + 1) for _ in range(nf + 1)]
    for i in range(nf + 1):
        D[i][0] = 0

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

    p_star = 0
    for p in range(Vp + 1):
        if D[nf][p] <= W:
            p_star = p
    return p_star


if __name__ == "__main__":
    w = [2, 3, 4]
    v = [30, 40, 50]
    W = 6
    p_star = llp_approx_knapsack(w, v, W, eps_num=1, eps_den=5)
    print("p* =", p_star, "(expect 24)")
    scale = (1 * 50) / (5 * 3)
    print("approx profit =", scale * p_star, "(expect ~80, OPT=80)")
