"""LLP form of Johnson reweighting: raise prices p[j] until every reduced edge weight is non-negative."""

def forbidden(j, p, pre, w):
    return any(p[j] < p[i] - w[i][j] for i in pre[j])


def advance(p, pre, w, n):
    new_p = list(p)
    for k in range(n):
        for i in pre[k]:
            cand = p[i] - w[i][k]
            if cand > new_p[k]:
                new_p[k] = cand
    for k in range(n):
        p[k] = new_p[k]


def llp_johnson(pre, w, n):
    p = [0] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if forbidden(j, p, pre, w):
                advance(p, pre, w, n)
                changed = True
                break
    return p


if __name__ == "__main__":
    # Same graph as LLPBellmanFord, but Johnson computes potentials.
    n = 4
    pre = [[], [0], [0, 1], [2]]
    inf = float('inf')
    w = [[0, 1, 4, inf],
         [inf, 0, -3, inf],
         [inf, inf, 0, 1],
         [inf, inf, inf, 0]]
    p = llp_johnson(pre, w, n)
    print("potentials:", p)
