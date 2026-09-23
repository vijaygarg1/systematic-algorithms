"""König's-theorem construction of a vertex cover of size |M| from a max matching."""

def par_vertex_cover_from_matching(adj, match_L):
    L = len(adj)
    R = len(adj[0])
    n = L + R
    C       = [False] * n
    partner = [-1] * n

    # Pass 1: every matched edge places its L endpoint into the cover.
    for u in range(L):
        v = match_L[u]
        if v != -1:
            C[u]            = True
            partner[u]      = L + v
            partner[L + v]  = u

    # Pass 2: cover any uncovered edge by swapping an endpoint's mark
    # to its uncovered partner.
    for u in range(L):
        for v in range(R):
            if adj[u][v] == 1 and not C[u] and not C[L + v]:
                if partner[u] != -1:
                    C[partner[u]] = False
                    C[u] = True
                else:
                    C[partner[L + v]] = False
                    C[L + v] = True
    return C


if __name__ == "__main__":
    # 3 left, 3 right.  Matching: (0, 0), (1, 2), (2, 1).
    adj = [
        [1, 1, 0],
        [1, 0, 1],
        [0, 1, 0],
    ]
    match_L = [0, 2, 1]
    C = par_vertex_cover_from_matching(adj, match_L)
    print("cover:", C)
    print("size:", sum(C))
