"""Disjoint-set with path compression in find and union-by-rank.

Auto-generated from UnionFind.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class UnionFind:
    def Find(self, parent, x):
        n = len(parent)
        if (parent[x] != x):
            parent[x] = self.Find(parent, parent[x])
        return parent[x]

    def Union(self, parent, rank, x, y):
        rx = self.Find(parent, x)
        ry = self.Find(parent, y)
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
    result = prog.Find(parent, x)
    print(result)

