"""Fulkerson reduction: glue chains via matched edges, contract by pointer-jumping."""

def parent_pointer_jumping(C):
    n = len(C)
    changed = True
    while changed:
        changed = False
        for i in range(n):
            p = C[i]
            if C[p] != p:
                C[i] = C[p]
                changed = True


def par_chain_cover_from_matching(match_partner):
    n = len(match_partner)
    C = list(range(n))
    for u in range(n):
        v = match_partner[u]
        if v != -1:
            C[u] = v
    parent_pointer_jumping(C)
    return C


if __name__ == "__main__":
    # Poset on 5 elements: chains [x1,x3] and [x2,x4,x5].
    # Strict-split matching pairs (x1^-, x3^+), (x2^-, x4^+), (x4^-, x5^+).
    # match_partner is indexed by L-vertex (= element id).
    match_partner = [2, 3, -1, 4, -1]   # 0->2, 1->3, 3->4
    print("chain roots:", par_chain_cover_from_matching(match_partner))
