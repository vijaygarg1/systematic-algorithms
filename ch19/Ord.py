"""Ord: multiplicative order of a modulo n via descending LLP on divisor lattice."""


def modpow(base, exp, mod):
    result = 1
    b = base % mod
    e = exp
    while e > 0:
        if e % 2 == 1:
            result = (result * b) % mod
        e //= 2
        b = (b * b) % mod
    return result


def ord_n(a, n, p, e):
    s = len(p)
    G = list(e)
    k = 1
    for i in range(s):
        k *= p[i] ** e[i]
    changed = True
    while changed:
        changed = False
        for i in range(s):
            if G[i] > 0 and modpow(a, k // p[i], n) == 1:
                G[i] -= 1
                k //= p[i]
                changed = True
    return k


if __name__ == "__main__":
    print(f"ord_7(2) = {ord_n(2, 7, [2, 3], [1, 1])}")
    print(f"ord_13(2) = {ord_n(2, 13, [2, 3], [2, 1])}")
