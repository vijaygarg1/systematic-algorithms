"""Classical in-degree zero queue algorithm for DAG layering."""

from collections import deque


def layering(pre, succ):
    n = len(pre)
    G = [0] * n
    indeg = [len(pre[j]) for j in range(n)]
    q = deque(j for j in range(n) if indeg[j] == 0)
    while q:
        j = q.popleft()
        for k in succ[j]:
            indeg[k] -= 1
            if indeg[k] == 0:
                G[k] = max((G[i] + 1 for i in pre[k]), default=0)
                q.append(k)
    return G


if __name__ == "__main__":
    # DAG: 0 -> 1, 2; 1 -> 3; 2 -> 3, 4; 3 -> 5; 4 -> 5.
    pre  = [[], [0], [0], [1, 2], [2], [3, 4]]
    succ = [[1, 2], [3], [3, 4], [5], [5], []]
    print("layers:", layering(pre, succ))
