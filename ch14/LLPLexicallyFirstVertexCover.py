"""LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge."""


def lex_less(a, b, c, d):
    amin, amax = min(a, b), max(a, b)
    cmin, cmax = min(c, d), max(c, d)
    if amin != cmin:
        return amin < cmin
    return amax < cmax


def is_lex_min_incident(i, j, adj, G):
    n = len(G)
    if adj[i][j] != 1 or G[i] or G[j]:
        return False
    for x in range(n):
        for y in range(x + 1, n):
            if adj[x][y] != 1 or G[x] or G[y]:
                continue
            if x in (i, j) or y in (i, j):
                if (x, y) != (i, j) and lex_less(x, y, i, j):
                    return False
    return True


def llp_lex_first_vertex_cover(adj):
    n = len(adj)
    G = [False] * n
    while True:
        changed = False
        for j in range(n):
            if G[j]:
                continue
            if any(is_lex_min_incident(i, j, adj, G) for i in range(n)):
                G[j] = True
                changed = True
        if not changed:
            return G


if __name__ == "__main__":
    adj = [[0, 1, 1, 0, 0],
           [1, 0, 0, 1, 0],
           [1, 0, 0, 1, 0],
           [0, 1, 1, 0, 1],
           [0, 0, 0, 1, 0]]
    print(llp_lex_first_vertex_cover(adj))
