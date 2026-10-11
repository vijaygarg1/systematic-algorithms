"""Classical augmenting-path bipartite matching, matching
bxx-matchingReduced.tex Algorithm BipartiteMatching (algo:seq-bipartite)."""


def alternating_path(j, adj, S, seen):
    m = len(adj[0])
    for k in range(m):
        if adj[j][k] == 1 and not seen[k]:
            seen[k] = True
            if S[k] == -1 or alternating_path(S[k], adj, S, seen):
                S[k] = j
                return True
    return False


def bipartite_matching(adj):
    n = len(adj)
    m = len(adj[0])
    G = [0] * n
    S = [-1] * m
    for j in range(n):
        seen = [False] * m
        if alternating_path(j, adj, S, seen):
            G[j] = 1
    return G, S


if __name__ == "__main__":
    # 3 left, 3 right.  Edges: 0-0, 0-1, 1-0, 2-1.
    adj = [
        [1, 1, 0],
        [1, 0, 0],
        [0, 1, 0],
    ]
    G, S = bipartite_matching(adj)
    print("G =", G)
    print("S (partner, per right vertex):", S)
    print("matching size:", sum(G))
