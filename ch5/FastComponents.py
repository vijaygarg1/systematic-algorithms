"""Fast connected components: SlowComponents accelerated by pointer
jumping.

The pointer-jumping clause G[j] >= G[G[j]] lets each round double the
reach of label propagation, reducing the number of rounds from
O(diameter) to O(log diameter).

  ensure(j) : G[j] >= G[G[j]]
  ensure(j) : G[j] >= max { G[i] | i in adj[j] }
"""


def fast_components(adj):
    n = len(adj)
    G = list(range(n))
    changed = True
    while changed:
        changed = False
        # Pointer-jump pass: each j follows its current label to the
        # label's label.
        for j in range(n):
            if G[j] < G[G[j]]:
                G[j] = G[G[j]]
                changed = True
        # Neighbour-max pass: every j adopts the largest label among
        # its neighbours, breaking out of local minima.
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
    print("labels:", fast_components(adj))
