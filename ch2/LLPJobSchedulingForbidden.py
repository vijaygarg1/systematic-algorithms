"""LLP job scheduling: forbidden when G[j] < max_{i in pre[j]} G[i] + t[j]."""

def forbidden(j, G, t, pre):
    if not pre[j]:
        return False
    return G[j] < max(G[i] + t[j] for i in pre[j])


def advance(j, G, t, pre):
    G[j] = max(G[i] + t[j] for i in pre[j])


def job_scheduling_forbidden(t, pre):
    n = len(t)
    G = list(t)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if forbidden(j, G, t, pre):
                advance(j, G, t, pre)
                changed = True
    return G


if __name__ == "__main__":
    # Six jobs.  pre[j] are the prerequisites of job j (0-based).
    t   = [3, 2, 4, 1, 2, 3]
    pre = [[], [0], [0], [1, 2], [2], [3, 4]]
    G = job_scheduling_forbidden(t, pre)
    print("G =", G)
