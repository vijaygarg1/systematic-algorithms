"""Connected components by ensure-clause label propagation."""

def slow_components(adj):
    n = len(adj)
    G = list(range(n))
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if adj[j]:
                m = max(G[i] for i in adj[j])
                if G[j] < m:
                    G[j] = m
                    changed = True
    return G


if __name__ == "__main__":
    # Two components: {0, 1, 2} and {3, 4}.
    adj = [
        [1, 2],     # 0
        [0, 2],     # 1
        [0, 1],     # 2
        [4],        # 3
        [3],        # 4
    ]
    print("labels:", slow_components(adj))
