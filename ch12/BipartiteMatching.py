"""Classical augmenting-path bipartite matching."""

def try_match(u, adj, partner, seen):
    m = len(adj[0])
    for v in range(m):
        if adj[u][v] == 1 and not seen[v]:
            seen[v] = True
            if partner[v] == -1 or try_match(partner[v], adj, partner, seen):
                partner[v] = u
                return True
    return False


def bipartite_matching(adj):
    n = len(adj)
    m = len(adj[0])
    G = [0] * n
    partner = [-1] * m
    for u in range(n):
        seen = [False] * m
        if try_match(u, adj, partner, seen):
            G[u] = 1
    return G, partner


if __name__ == "__main__":
    # 3 left, 3 right.  Edges: 0-0, 0-1, 1-0, 2-1.
    adj = [
        [1, 1, 0],
        [1, 0, 0],
        [0, 1, 0],
    ]
    G, partner = bipartite_matching(adj)
    print("G =", G)
    print("partner (per right vertex):", partner)
    print("matching size:", sum(G))
