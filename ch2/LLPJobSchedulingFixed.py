"""LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed."""

def job_scheduling_fixed(t, pre):
    n = len(t)
    G = list(t)
    fixed = [len(pre[k]) == 0 for k in range(n)]
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if not fixed[j] and all(fixed[i] for i in pre[j]):
                G[j] = max(G[i] + t[j] for i in pre[j])
                fixed[j] = True
                changed = True
    return G


if __name__ == "__main__":
    t   = [3, 2, 5, 1]
    pre = [[], [0], [0], [1, 2]]
    print("G =", job_scheduling_fixed(t, pre))
