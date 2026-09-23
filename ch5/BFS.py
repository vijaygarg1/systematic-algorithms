"""Sequential BFS distance from a source vertex (FIFO queue)."""

import math
from collections import deque


def bfs(dep, s):
    n = len(dep)
    G = [math.inf] * n
    G[s] = 0
    q = deque([s])
    while q:
        j = q.popleft()
        for k in dep[j]:
            if G[k] > G[j] + 1:
                G[k] = G[j] + 1
                q.append(k)
    return G


if __name__ == "__main__":
    # Sample DAG / graph adjacency (out-edges per vertex).
    dep = [
        [1, 2],     # 0 -> 1, 2
        [3],        # 1 -> 3
        [3, 4],     # 2 -> 3, 4
        [5],        # 3 -> 5
        [5],        # 4 -> 5
        [],         # 5 (sink)
    ]
    print("BFS from 0:", bfs(dep, 0))
