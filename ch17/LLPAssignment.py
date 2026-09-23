"""LLP assignment: minimum clearing price vector via step-jump price increments.
Each iteration: identify overdemanded items, raise each by step[j] = min slack
to the next critical price."""


def try_match(b, v, C, partner, seen):
    n = len(C)
    best_surplus = float("-inf")
    for i in range(n):
        s = v[b][i] - C[i]
        if s > best_surplus:
            best_surplus = s
    for i in range(n):
        if v[b][i] - C[i] == best_surplus and not seen[i]:
            seen[i] = True
            if partner[i] == -1 or try_match(partner[i], v, C, partner, seen):
                partner[i] = b
                return True
    return False


def check_perfect_matching(v, C):
    n = len(C)
    m = len(v)
    partner = [-1] * n
    matched = 0
    for b in range(m):
        seen = [False] * n
        if try_match(b, v, C, partner, seen):
            matched += 1
    return matched == m


def raise_overdemanded_prices(v, C):
    n = len(C)
    m = len(v)
    # Snapshot bestSurplus[b] before any prices change this round.
    best_surplus = [float("-inf")] * m
    for b in range(m):
        for i in range(n):
            s = v[b][i] - C[i]
            if s > best_surplus[b]:
                best_surplus[b] = s
    # step[j] = min slack across bidders whose top choice includes j.
    step = [float("inf")] * n
    demand = [0] * n
    for j in range(n):
        for b in range(m):
            if v[b][j] - C[j] != best_surplus[b]:
                continue
            demand[j] += 1
            second_best = float("-inf")
            for i in range(n):
                if i == j:
                    continue
                s = v[b][i] - C[i]
                if s > second_best:
                    second_best = s
            slack = (v[b][j] - C[j]) - second_best
            if slack < step[j]:
                step[j] = slack
        if step[j] < 1:
            step[j] = 1
    for j in range(n):
        if demand[j] > 1:
            C[j] += step[j]


def llp_assignment(v):
    n = len(v[0])
    C = [0] * n
    while not check_perfect_matching(v, C):
        raise_overdemanded_prices(v, C)
    return C


if __name__ == "__main__":
    v = [[5, 3, 1], [4, 4, 2], [1, 2, 5]]
    result = llp_assignment(v)
    print(f"clearing prices = {result}")
