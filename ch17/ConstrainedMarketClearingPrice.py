"""LLP market clearing price: forbidden when item j is in an overdemanded set;
advance raises its price by 1."""


def is_over_demanded(j, v, G):
    n = len(G)
    m = len(v)
    demand = 0
    for b in range(m):
        best = v[b][j] - G[j]
        is_best = True
        for i in range(n):
            if v[b][i] - G[i] > best:
                is_best = False
                break
        if is_best:
            demand += 1
    return demand > 1


def constrained_market_clearing_price(v):
    n = len(v[0])
    G = [0] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if is_over_demanded(j, v, G):
                G[j] += 1
                changed = True
    return G


if __name__ == "__main__":
    v = [[5, 3, 1], [4, 4, 2], [1, 2, 5]]
    result = constrained_market_clearing_price(v)
    print(f"clearing prices = {result}")
