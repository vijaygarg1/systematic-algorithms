"""LLP-Boruvka: the book's recursive Boruvka(V,E), written as a loop (tail
recursion) so the pointer-jumping kernel stays a clean fixpoint step. Each
round selects every active vertex's minimum-weight edge (breaking a mutual
mwe tie toward the smaller-numbered vertex), pointer-jumps to rooted stars,
records every active vertex's own mwe edge in the tree, keeps only the
roots active, and repeats until one component remains. `progress` guards
against a disconnected graph or an isolated vertex (no outgoing edge at
all) -- outside the book's own stated connected-graph assumption -- so the
loop cannot spin forever waiting for a spanning tree that cannot exist."""

INF = float("inf")


def find(v, g):
    while g[v] != v:
        v = g[v]
    return v


def other_endpoint(e, u, v, x):
    return v[e] if u[e] == x else u[e]


def llp_boruvka(n, u, v, w):
    m = len(u)
    t = [False] * m
    g = list(range(n))
    active = [True] * n

    progress = True
    while sum(active) > 1 and progress:
        before = sum(active)
        mwe_edge = [-1] * n
        mwe_w = [INF] * n
        for e in range(m):
            ru, rv = find(u[e], g), find(v[e], g)
            if active[ru] and active[rv] and ru != rv:
                if w[e] < mwe_w[ru]:
                    mwe_w[ru], mwe_edge[ru] = w[e], e
                if w[e] < mwe_w[rv]:
                    mwe_w[rv], mwe_edge[rv] = w[e], e

        for i in range(n):
            if active[i]:
                if mwe_edge[i] == -1:
                    g[i] = i
                else:
                    nb = other_endpoint(mwe_edge[i], u, v, i)
                    nb_edge = mwe_edge[nb]
                    if (nb_edge != -1 and other_endpoint(nb_edge, u, v, nb) == i
                            and i < nb):
                        g[i] = i
                    else:
                        g[i] = nb

        changed = True
        while changed:
            changed = False
            for j in range(n):
                if active[j] and g[j] != g[g[j]]:
                    g[j] = g[g[j]]
                    changed = True

        for i in range(n):
            if active[i] and mwe_edge[i] != -1:
                t[mwe_edge[i]] = True
        for i in range(n):
            if active[i] and g[i] != i:
                active[i] = False
        progress = sum(active) < before

    return t


if __name__ == "__main__":
    # Same 5-vertex graph as the Kruskal/Prim/Boruvka demos.
    u = [1, 0, 0, 1, 2, 2, 3]
    v = [2, 1, 2, 3, 3, 4, 4]
    w = [1, 2, 3, 4, 5, 6, 7]
    print(llp_boruvka(5, u, v, w))
