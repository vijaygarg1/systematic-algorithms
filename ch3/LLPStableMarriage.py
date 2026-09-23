"""LLP form of Gale-Shapley on the proposal-vector lattice."""

def forbidden(j, G, mpref, rank):
    n = len(G)
    for i in range(n):
        for k in range(G[i] + 1):
            if (mpref[j][G[j]] == mpref[i][k]
                    and rank[mpref[j][G[j]]][i] < rank[mpref[j][G[j]]][j]):
                return True
    return False


def advance(j, G):
    G[j] += 1


def stable_marriage(mpref, rank, I):
    n = len(I)
    G = list(I)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if forbidden(j, G, mpref, rank):
                advance(j, G)
                changed = True
    return G


if __name__ == "__main__":
    # Three men, three women, 0-based.
    mpref = [
        [0, 1, 2],
        [1, 0, 2],
        [0, 1, 2],
    ]
    rank = [
        [2, 1, 3],
        [1, 2, 3],
        [1, 2, 3],
    ]
    I = [0, 0, 0]
    G = stable_marriage(mpref, rank, I)
    for j in range(len(G)):
        print(f"man {j}: index {G[j]}, matched with woman {mpref[j][G[j]]}")
