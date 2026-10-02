"""LLP weighted interval scheduling: G[j] >= max(G[j-1], w[j] + G[p[j]])."""

def rhs(j, G, w, p):
    skip = G[j - 1]
    take = w[j] + G[p[j]]
    return max(skip, take)


def llp_wis(w, p):
    n = len(w)
    G = [0] * n
    changed = True
    while changed:
        changed = False
        for j in range(1, n):
            v = rhs(j, G, w, p)
            if G[j] < v:
                G[j] = v
                changed = True
    return G


if __name__ == "__main__":
    w = [0, 4, 6, 5, 3, 7]
    p = [0, 0, 0, 1, 3, 2]
    G = llp_wis(w, p)
    print("G =", G)
    print(f"optimum = G[{len(G) - 1}] = {G[-1]}")
