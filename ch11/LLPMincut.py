"""LLP driver for the least mincut satisfying a lattice-linear side predicate."""

def llp_mincut(C, Bcheck, forbidden_for_B, next_mincut):
    while True:
        # Inner: drive every B-forbidden j to True.
        changed = True
        while changed:
            changed = False
            for j in range(len(C)):
                if forbidden_for_B(j, C):
                    if C[j]:
                        return None       # already True ↔ no extension exists
                    C[j] = True
                    changed = True
        # Now C is closed under B; jump to the least mincut >= C.
        if Bcheck(C):
            return C
        nxt = next_mincut(C)
        if nxt is None:
            return None
        C = nxt


if __name__ == "__main__":
    # Trivial concrete demo: B = "always true", next_mincut = identity.
    n = 4
    C = [True, False, False, False]   # s = 0 in S; t = 3 not in S
    print("result:", llp_mincut(
        list(C),
        Bcheck=lambda c: True,
        forbidden_for_B=lambda j, c: False,
        next_mincut=lambda c: c,
    ))
