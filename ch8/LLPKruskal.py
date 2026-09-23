"""LLP-Kruskal: edge-inclusion lattice driven by union-find."""

def find(x, parent):
    while parent[x] != x:
        parent[x] = parent[parent[x]]    # path-halving
        x = parent[x]
    return x


def union(a, b, parent):
    ra = find(a, parent)
    rb = find(b, parent)
    if ra != rb:
        parent[ra] = rb


def llp_kruskal(u, v, parent):
    m = len(u)
    C = [False] * m
    changed = True
    while changed:
        changed = False
        for j in range(m):
            if not C[j] and find(u[j], parent) != find(v[j], parent):
                C[j] = True
                union(u[j], v[j], parent)
                changed = True
    return C


if __name__ == "__main__":
    # Same example as Kruskal.py: 4 vertices, 5 weight-sorted edges.
    u = [0, 1, 0, 1, 2]
    v = [1, 2, 2, 3, 3]
    n = 4
    parent = list(range(n))
    print("C:", llp_kruskal(u, v, parent))
