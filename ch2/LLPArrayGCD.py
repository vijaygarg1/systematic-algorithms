"""LLP-GCD: forbidden when some G[i] < G[j]; advance shrinks G[j] via mod."""

def forbidden(j, G):
    for i in range(len(G)):
        if i != j and G[j] > G[i]:
            return i
    return None


def advance(j, i, G):
    if G[j] % G[i] == 0:
        G[j] = G[i]
    else:
        G[j] = G[j] % G[i]


def llp_gcd(A):
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
    for A in [[48, 18], [100, 75, 50], [60, 36, 24]]:
        print(f"llp_gcd({A}) = {llp_gcd(A)}")
