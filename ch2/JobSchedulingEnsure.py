"""LLP job scheduling, ensure-sugar form of the same recurrence."""

def job_scheduling_ensure(t, pre):
    n = len(t)
    G = list(t)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if pre[j]:
                rhs = max(G[i] + t[j] for i in pre[j])
                if G[j] < rhs:
                    G[j] = rhs
                    changed = True
    return G


if __name__ == "__main__":
    t   = [3, 2, 4, 1, 2, 3]
    pre = [[], [0], [0], [1, 2], [2], [3, 4]]
    print("G =", job_scheduling_ensure(t, pre))
