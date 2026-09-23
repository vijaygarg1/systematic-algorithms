"""Disjoint-set with path compression in find and union-by-rank."""

def find(parent, x):
    if parent[x] != x:
        parent[x] = find(parent, parent[x])
    return parent[x]


def union(parent, rank, x, y):
    rx = find(parent, x)
    ry = find(parent, y)
    if rx == ry:
        return False
    if rank[rx] < rank[ry]:
        parent[rx] = ry
    elif rank[rx] > rank[ry]:
        parent[ry] = rx
    else:
        parent[ry] = rx
        rank[rx] += 1
    return True


if __name__ == "__main__":
    n = 6
    parent = list(range(n))
    rank = [0] * n
    for x, y in [(0, 1), (2, 3), (1, 2)]:
        merged = union(parent, rank, x, y)
        print(f"union({x}, {y}) -> merged={merged}")
    print("roots:", [find(parent, x) for x in range(n)])
