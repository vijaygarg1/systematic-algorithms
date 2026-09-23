"""LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1."""

import math


def forbidden(j, G, pre):
    if not pre[j]:
        return False
    return G[j] > min(G[i] + 1 for i in pre[j])


def advance(j, G, pre):
    G[j] = min(G[i] + 1 for i in pre[j])


def llp_bfs(pre, G):
    n = len(G)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if forbidden(j, G, pre):
                advance(j, G, pre)
                changed = True
    return G


if __name__ == "__main__":
    # Predecessor lists for the same graph as in BFS.py.
    pre = [
        [],          # 0 is the source
        [0],
        [0],
        [1, 2],
        [2],
        [3, 4],
    ]
    n = len(pre)
    G = [math.inf] * n
    G[0] = 0
    print("BFS distances:", llp_bfs(pre, G))
