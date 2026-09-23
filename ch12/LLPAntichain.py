"""LLP-Antichain: maximum antichain by advancing chain indices to dominate-free positions."""

def llp_antichain(chains, leq):
    m = len(chains)
    length = [len(c) for c in chains]
    G = [0] * m
    changed = True
    while changed:
        changed = False
        for j in range(m):
            if G[j] >= length[j]:
                continue
            x = chains[j][G[j]]
            dominated = False
            for k in range(m):
                if k == j or G[k] >= length[k]:
                    continue
                y = chains[k][G[k]]
                if x != y and leq[x][y]:
                    dominated = True
                    break
            if dominated:
                G[j] += 1
                changed = True
    return G, length


if __name__ == "__main__":
    # Book's 5-element poset: x1 < x2 < x3, x4 < x5, x2 < x5
    # Use a *non-minimum* cover to show advancing in action.
    chains = [[0, 1, 2], [3], [4]]
    # leq is the reflexive + transitive closure of the relation.
    leq = [
        [True,  True,  True,  False, True ],   # x1
        [False, True,  True,  False, True ],   # x2
        [False, False, True,  False, False],   # x3
        [False, False, False, True,  True ],   # x4
        [False, False, False, False, True ],   # x5
    ]
    G, length = llp_antichain(chains, leq)
    antichain = [chains[j][G[j]] for j in range(len(G)) if G[j] < length[j]]
    print(f"G = {G}")
    print(f"antichain = {[f'x{i+1}' for i in antichain]}")
