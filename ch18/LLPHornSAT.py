"""LLP Horn SAT: forbidden when an implication's antecedents are all true
but the consequent x_j is false; advance sets x_j to true."""


def horn_implied(j, G, body, head):
    if G[j]:
        return False
    for c in range(len(body)):
        if head[c] == j:
            if all(G[x] for x in body[c]):
                return True
    return False


def horn_sat_llp(body, head, n):
    G = [False] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if horn_implied(j, G, body, head):
                G[j] = True
                changed = True
    return G


if __name__ == "__main__":
    body = [[], [0], [1]]
    head = [0, 1, 2]
    result = horn_sat_llp(body, head, 3)
    print(f"least model = {result}")
