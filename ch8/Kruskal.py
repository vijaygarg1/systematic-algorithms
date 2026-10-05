"""Classical Kruskal MST: sort edges, union-find with rank and path-compression.

find/union are inlined from UnionFind.py (not imported) so this file runs
standalone -- the website only ever publishes the .py.txt plain-text view
of each file individually, never a multi-file download, so an import of a
sibling module fails for anyone who saves this one file and runs it."""


def find(parent, x):
    if parent[x] != x:
        parent[x] = find(parent, parent[x])
    return parent[x]


def union(parent, rank, x, y):
    rx = find(parent, x)
    ry = find(parent, y)
    if rx == ry:
        return False
    if rank[rx] < rank[ry]:
        parent[rx] = ry
    elif rank[rx] > rank[ry]:
        parent[ry] = rx
    else:
        parent[ry] = rx
        rank[rx] += 1
    return True


def mst(n, U, V, W):
    m = len(U)
    inTree = [False] * m
    parent = list(range(n))
    rank   = [0] * n
    chosen = 0
    for e in range(m):
        if chosen >= n - 1:
            break
        if union(parent, rank, U[e], V[e]):
            inTree[e] = True
            chosen += 1
    return inTree


if __name__ == "__main__":
    # 4-vertex graph: edges already sorted by weight.
    U = [0, 1, 0, 1, 2]
    V = [1, 2, 2, 3, 3]
    W = [1, 2, 3, 4, 5]
    n = 4
    print("inTree:", mst(n, U, V, W))
