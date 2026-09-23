"""Generic queue-based reachability from vertex 0."""

from collections import deque


def traversal(dep):
    n = len(dep)
    G = [0] * n
    G[0] = 1
    q = deque([0])
    while q:
        j = q.popleft()
        for k in dep[j]:
            if G[k] == 0:
                G[k] = 1
                q.append(k)
    return G


if __name__ == "__main__":
    dep = [
        [1, 2],
        [3],
        [3, 4],
        [5],
        [5],
        [],
        [7],   # vertex 6 - unreachable from 0
        [],    # vertex 7 - unreachable from 0
    ]
    print("reachable:", traversal(dep))
