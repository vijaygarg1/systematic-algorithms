"""Reachability as a 1-bit LLP: forbidden = unreached vertex with reached predecessor."""

def llp_traversal(pre, G):
    n = len(G)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if not G[j] and any(G[i] for i in pre[j]):
                G[j] = True
                changed = True
    return G


if __name__ == "__main__":
    pre = [[], [0], [0], [1, 2], [2], [3, 4]]
    n = len(pre)
    G = [False] * n
    G[0] = True
    print("reachable:", llp_traversal(pre, G))
