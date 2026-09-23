"""Precedences: composition program enforcing G[j] >= G[i] + t[i] for
every i in pre(j)."""


def precedences(pre, t, G):
    n = len(G)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            target = G[j]
            for i in pre[j]:
                target = max(target, G[i] + t[i])
            if target > G[j]:
                G[j] = target
                changed = True
    return G


if __name__ == "__main__":
    pre = [set(), {0}, {0, 1}]
    t = [3, 4, 2]
    G = [0, 0, 0]
    print(precedences(pre, t, G))
