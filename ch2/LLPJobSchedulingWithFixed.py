"""Kahn's-style topological sweep: finalise each job once its predecessors are fixed."""

def job_scheduling_with_fixed(t, pre, succ):
    n = len(t)
    G     = list(t)
    count = [len(pre[k]) for k in range(n)]
    queue = [k for k in range(n) if count[k] == 0]
    head = 0
    while head < len(queue):
        j = queue[head]
        head += 1
        if pre[j]:
            G[j] = max(G[j], max(G[i] + t[j] for i in pre[j]))
        for k in succ[j]:
            count[k] -= 1
            if count[k] == 0:
                queue.append(k)
    return G


if __name__ == "__main__":
    t    = [3, 2, 4, 1, 2, 3]
    pre  = [[], [0], [0], [1, 2], [2], [3, 4]]
    succ = [[1, 2], [3], [3, 4], [5], [5], []]
    print("G =", job_scheduling_with_fixed(t, pre, succ))
