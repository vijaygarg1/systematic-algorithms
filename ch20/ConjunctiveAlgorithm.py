"""ConjunctiveAlgorithm: detect a conjunctive predicate on a distributed computation."""


def happened_before(j, G, vc, n):
    for i in range(n):
        if i != j and vc[j * n + G[j]][i] >= G[i]:
            return True
    return False


def conjunctive_algorithm(vc, T):
    n = len(T)
    G = [1] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if happened_before(j, G, vc, n):
                if G[j] >= T[j]:
                    return None
                else:
                    G[j] += 1
                    changed = True
    return G


if __name__ == "__main__":
    n = 2
    T = [3, 3]
    vc = [[0] * n for _ in range((n * (T[0] + 1)))]
    vc[0 * n + 1][1] = 0
    vc[0 * n + 2][1] = 1
    vc[0 * n + 3][1] = 2
    vc[1 * n + 1][0] = 0
    vc[1 * n + 2][0] = 1
    vc[1 * n + 3][0] = 2
    print(f"conjunctive_algorithm result = {conjunctive_algorithm(vc, T)}")
