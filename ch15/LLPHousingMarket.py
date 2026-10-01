"""LLP housing market: forbidden when agent j is not in S(G) (the
largest submatching) but wishes for a house held by an agent who is;
advance moves j to its next preference. S(G) is the set of agents
lying on a cycle of the wish functional graph (i -> wish(i))."""


def wish(i, G, pref):
    return pref[i][G[i]]


def in_submatching(j, G, pref):
    n = len(G)
    cur = wish(j, G, pref)
    steps = 1
    while steps <= n:
        if cur == j:
            return True
        cur = wish(cur, G, pref)
        steps += 1
    return False


def llp_housing_market(pref):
    n = len(pref)
    G = [0] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if not in_submatching(j, G, pref) and in_submatching(wish(j, G, pref), G, pref):
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
