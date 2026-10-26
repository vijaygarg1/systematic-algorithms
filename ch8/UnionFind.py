"""Disjoint-set with path compression in find and union-by-rank.

Auto-generated from UnionFind.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class UnionFind:
    def find(self, parent, x):
        n = len(parent)
        if (parent[x] != x):
            parent[x] = self.find(parent, parent[x])
        return parent[x]

    def union(self, parent, rank, x, y):
        rx = self.find(parent, x)
        ry = self.find(parent, y)
        if (rx == ry):
            return False
        if (rank[rx] < rank[ry]):
            parent[rx] = ry
        else:
            if (rank[rx] > rank[ry]):
                parent[ry] = rx
            else:
                parent[ry] = rx
                rank[rx] = (rank[rx] + 1)
        return True


if __name__ == '__main__':
    parent = [3, 1, 6, 1, 6, 3, 6, 4]
    x = 0
    prog = UnionFind()
    result = prog.find(parent, x)
    print(result)

