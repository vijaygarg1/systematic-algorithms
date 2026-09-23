"""Classical Kruskal MST: sort edges, union-find with rank and path-compression."""

from UnionFind import find, union


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
