"""LLP-Kruskal: edge-inclusion lattice driven by union-find.
Calls the sibling UnionFind program (`UnionFind.find(parent, x)`,
`UnionFind.union(parent, rank, x, y)`) instead of keeping its own
copy of find/union -- same multi-file treatment as Kruskal.llp. This
makes LLPKruskal.java a multi-file program: it requires UnionFind.java
alongside it to compile (see JAVA_MULTI_FILE in validate-site.py and
the `javaRequires` demo-spec field in llp.js).

Auto-generated from LLPKruskal.llp by lang/llcpy.py.
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


class LLPKruskal:
    def LLPKruskal(self, u, v, parent):
        self.u = u
        self.v = v
        self.parent = parent
        self.n = len(self.u)
        self.G = [False] * (self.n)
        self.rank = [0] * (len(self.parent))
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if ((not self.G[j]) and (UnionFind().find(self.parent, self.u[j]) != UnionFind().find(self.parent, self.v[j]))):
                    self.G[j] = True
                    UnionFind().union(self.parent, self.rank, self.u[j], self.v[j])
                    _changed = True
        return self.G


if __name__ == '__main__':
    u = [0, 0, 1, 2]
    v = [0, 0, 1, 1]
    parent = [0, 0, 0, 2]
    prog = LLPKruskal()
    result = prog.LLPKruskal(u, v, parent)
    print(result)

