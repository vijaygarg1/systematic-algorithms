"""König's theorem: minimum vertex cover of size |M| from a maximum
bipartite matching, via alternating reachability. Matches
bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set of
vertices reachable from an unmatched L-vertex by an alternating path
(non-matching edge, then matching edge, ...); C := (L \\ Z) union (R
intersect Z).
"""

def par_vertex_cover_from_matching(adj, match_L):
    L = len(adj)
    R = len(adj[0])
    n = L + R
    in_Z    = [False] * n
    partner = [-1] * n

    for u in range(L):
        v = match_L[u]
        if v != -1:
            partner[u]     = L + v
            partner[L + v] = u

    # BFS queue of vertices whose incident edges are still unexplored,
    # seeded with every unmatched L-vertex.
    Q = []
    for u in range(L):
        if match_L[u] == -1:
            in_Z[u] = True
            Q.append(u)

    head = 0
    while head < len(Q):
        w = Q[head]
        head += 1
        if w < L:
            # From an L-vertex, follow every non-matching edge.
            for v in range(R):
                if adj[w][v] == 1 and partner[w] != L + v and not in_Z[L + v]:
                    in_Z[L + v] = True
                    Q.append(L + v)
        else:
            # From an R-vertex, follow its matching edge (if any).
            p = partner[w]
            if p != -1 and not in_Z[p]:
                in_Z[p] = True
                Q.append(p)

    C = [False] * n
    for u in range(L):
        C[u] = not in_Z[u]
    for v in range(R):
        C[L + v] = in_Z[L + v]
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
