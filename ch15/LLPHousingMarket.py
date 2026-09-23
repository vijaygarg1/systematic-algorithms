"""LLP housing market: forbidden when agent j is not in the submatching
but wishes for a house that is in the submatching; advance increments proposal."""


def in_submatching(j, G, pref):
    n = len(G)
    target = pref[j][G[j]]
    for i in range(n):
        if i != j and pref[i][G[i]] == target:
            return False
    return True


def wish_in_submatching(j, G, pref):
    n = len(G)
    wish = pref[j][G[j]]
    for i in range(n):
        if pref[i][G[i]] == wish and in_submatching(i, G, pref):
            return True
    return False


def llp_housing_market(pref):
    n = len(pref)
    G = [0] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if not in_submatching(j, G, pref) and wish_in_submatching(j, G, pref):
                G[j] += 1
                changed = True
    return G


if __name__ == "__main__":
    pref = [[1, 0, 2], [2, 1, 0], [0, 2, 1]]
    result = llp_housing_market(pref)
    print(f"proposal vector = {result}")
    print(f"allocation: agent i gets house pref[i][G[i]]")
    for i, g in enumerate(result):
        print(f"  agent {i} -> house {pref[i][g]}")
