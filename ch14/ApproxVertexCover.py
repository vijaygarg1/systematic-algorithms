"""2-approximation vertex cover: greedily pick both endpoints of an uncovered edge."""


def approx_vertex_cover(adj):
    n = len(adj)
    C = [False] * n
    removed = [False] * n
    while True:
        progress = False
        for u in range(n):
            if removed[u]:
                continue
            for v in range(u + 1, n):
                if removed[v] or adj[u][v] != 1:
                    continue
                C[u] = True
                C[v] = True
                removed[u] = True
                removed[v] = True
                progress = True
                break
            if removed[u]:
                break
        if not progress:
            return C


if __name__ == "__main__":
    # Path 0-1-2-3-4 (5 vertices).
    adj = [[0, 1, 0, 0, 0],
           [1, 0, 1, 0, 0],
           [0, 1, 0, 1, 0],
           [0, 0, 1, 0, 1],
           [0, 0, 0, 1, 0]]
    print(approx_vertex_cover(adj))
