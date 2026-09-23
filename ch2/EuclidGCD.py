"""LLP form of Euclid: forbidden when some G[i] < G[j]; advance subtracts."""

def forbidden(j, G):
    for i in range(len(G)):
        if i != j and G[j] > G[i]:
            return i
    return None


def advance(j, i, G):
    G[j] = G[j] - G[i]


def euclid_gcd(A):
    G = list(A)
    changed = True
    while changed:
        changed = False
        for j in range(len(G)):
            i = forbidden(j, G)
            if i is not None:
                advance(j, i, G)
                changed = True
    return G


if __name__ == "__main__":
    for A in [[48, 18], [100, 75], [60, 36, 24]]:
        print(f"euclid_gcd({A}) = {euclid_gcd(A)}")
