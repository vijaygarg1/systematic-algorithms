"""LLP-Layering: longest path from a source to each vertex.

The `fixed` array here is the SCHEDULE -- priority(j) = max{G[i]+1} --
not part of the forbidden predicate, which is just the negation of
B_layer at j. Scheduling this way advances each vertex exactly once.
"""

def llp_layering(pre):
    n = len(pre)
    G     = [0] * n
    fixed = [False] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if not fixed[j] and all(fixed[i] for i in pre[j]):
                G[j] = max((G[i] + 1 for i in pre[j]), default=0)
                fixed[j] = True
                changed = True
    return G


if __name__ == "__main__":
    pre = [[], [0], [0], [1, 2], [2], [3, 4]]
    print("LLP layers:", llp_layering(pre))
