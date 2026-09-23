"""LLP pointer-jumping kernel of Borůvka's algorithm."""

def llp_boruvka(G):
    changed = True
    while changed:
        changed = False
        for j in range(len(G)):
            if G[j] != G[G[j]]:
                G[j] = G[G[j]]
                changed = True
    return G


if __name__ == "__main__":
    # 8-vertex rooted forest: G[j] is j's parent (G[root] == root).
    # Two trees: rooted at 0 (chain 0 <- 1 <- 2 <- 3) and at 4 (4 <- 5 <- 6 <- 7).
    G = [0, 0, 1, 2, 4, 4, 5, 6]
    print("after pointer-jumping:", llp_boruvka(G))
