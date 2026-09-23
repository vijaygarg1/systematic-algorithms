"""Classical Boruvka MST: repeatedly attach every component to its
cheapest outgoing edge until one component remains."""

from collections import deque


def _components(n, U, V, inTree):
    cid = [0] * n
    visited = [False] * n
    for start in range(n):
        if visited[start]:
            continue
        visited[start] = True
        cid[start] = start
        q = deque([start])
        while q:
            v = q.popleft()
            for e in range(len(U)):
                if not inTree[e]:
                    continue
                a, b = U[e], V[e]
                u = b if a == v else (a if b == v else -1)
                if u != -1 and not visited[u]:
                    visited[u] = True
                    cid[u] = cid[start]
                    q.append(u)
    return cid


def mst(n, U, V, W):
    m = len(U)
    inTree = [False] * m
    treeEdges = 0
    while treeEdges < n - 1:
        cid = _components(n, U, V, inTree)

        mwe = [-1] * n
        dist = [float("inf")] * n
        for e in range(m):
            i, j = U[e], V[e]
            if cid[i] != cid[j]:
                if W[e] < dist[cid[i]]:
                    dist[cid[i]] = W[e]
                    mwe[cid[i]] = e
                if W[e] < dist[cid[j]]:
                    dist[cid[j]] = W[e]
                    mwe[cid[j]] = e

        for i in range(n):
            if cid[i] == i and mwe[i] != -1 and not inTree[mwe[i]]:
                inTree[mwe[i]] = True
                treeEdges += 1
    return inTree


if __name__ == "__main__":
    # 5-vertex graph matching the book's running example (Fig. mst-graph).
    U = [0, 1, 0, 3, 1, 2]
    V = [2, 2, 3, 4, 3, 4]
    W = [4, 3, 7, 2, 9, 11]
    print("inTree:", mst(5, U, V, W))
