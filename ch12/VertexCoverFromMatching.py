"""König's theorem: minimum vertex cover of size |M| from a maximum
bipartite matching, via alternating reachability. Matches
bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set of
vertices reachable from an unmatched L-vertex by an alternating path
(non-matching edge, then matching edge, ...); C := (L \\ Z) union (R
intersect Z). The book treats the matching M as given directly as a
partner lookup; here it is given as match_L[j] = R-partner of L-vertex j
(or -1 if unmatched), with match_R (the same matching read from the R
side -- the only direction the book's "partner[w]" needs, since w is
always an R-vertex at that point in the algorithm) derived from it.
"""

def par_vertex_cover_from_matching(adj, match_L):
    L = len(adj)
    R = len(adj[0])
    match_R = [-1] * R
    for j in range(L):
        if match_L[j] != -1:
            match_R[match_L[j]] = j

    # Z := set of unmatched vertices in L; Q := Z.
    in_Z = [False] * (L + R)
    Q = []
    for j in range(L):
        if match_L[j] == -1:
            in_Z[j] = True
            Q.append(j)

    head = 0
    while head < len(Q):
        w = Q[head]
        head += 1
        if w < L:
            # w in L: follow every (w, v) in E \ M with v not in Z.
            for v in range(R):
                if adj[w][v] == 1 and match_L[w] != v and not in_Z[L + v]:
                    in_Z[L + v] = True
                    Q.append(L + v)
        else:
            # w in R: if w is matched and partner[w] not in Z, add it.
            r = w - L
            if match_R[r] != -1 and not in_Z[match_R[r]]:
                in_Z[match_R[r]] = True
                Q.append(match_R[r])

    # C := (L \ Z) union (R intersect Z).
    C = [False] * (L + R)
    for j in range(L):
        C[j] = not in_Z[j]
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
