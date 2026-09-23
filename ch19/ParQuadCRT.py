"""LLP-QuadCRT: simultaneous quadratic congruences x^2 = a[j] (mod m[j]).

Ascending LLP that, given precomputed roots[j] (a square root of a[j] mod
m[j]), drives every G[j] up by multiples of m[j] until they coincide on a
value satisfying every congruence."""


def par_quad_crt(m, a, roots):
    n = len(m)
    G = list(roots)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            for i in range(n):
                if G[j] < G[i]:
                    diff = G[i] - G[j]
                    steps = (diff + m[j] - 1) // m[j]
                    G[j] = G[j] + steps * m[j]
                    changed = True
                    break
    return G


if __name__ == "__main__":
    # x^2 = 1 (mod 5), x^2 = 1 (mod 7) -> x = 1 satisfies both
    m = [5, 7]
    a = [1, 1]
    roots = [1, 1]   # smallest non-negative roots
    print(f"G = {par_quad_crt(m, a, roots)}")
