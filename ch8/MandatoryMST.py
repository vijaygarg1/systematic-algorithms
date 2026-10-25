"""MandatoryMST: composition program forcing the edges in subset M
into the spanning tree.  Halts "infeasible" (returns null) when a
mandatory edge closes a cycle with previously committed mandatory
edges; equivalently, when M itself is not acyclic.  Composed onto
LLP-Kruskal via predicate conjunction:
[ LLP-Kruskal(E, w, G) && MandatoryMST(M, G) ].
Calls the sibling UnionFind program (`UnionFind.Find(parent, x)`,
`UnionFind.Union(parent, rank, x, y)`) instead of keeping its own
copy of find/union -- same multi-file treatment as Kruskal.llp. This
makes MandatoryMST.java a multi-file program: it requires
UnionFind.java alongside it to compile (see JAVA_MULTI_FILE in
validate-site.py and the `javaRequires` demo-spec field in llp.js).

Auto-generated from MandatoryMST.llp by lang/llcpy.py.
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


class MandatoryMST:
    def MandatoryMST(self, u, v, M, parent):
        self.u = u
        self.v = v
        self.M = M
        self.parent = parent
        self.n = len(self.u)
        self.G = [False] * (self.n)
        self.rank = [0] * (len(self.parent))
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (self.M[j] and (not self.G[j])):
                    if (UnionFind().Find(self.parent, self.u[j]) == UnionFind().Find(self.parent, self.v[j])):
                        return None
                    self.G[j] = True
                    UnionFind().Union(self.parent, self.rank, self.u[j], self.v[j])
                    _changed = True
        return self.G


if __name__ == '__main__':
    u = [0, 0, 1, 2]
    v = [0, 0, 1, 1]
    M = [False, False, False, False]
    parent = [0, 0, 0, 2]
    prog = MandatoryMST()
    result = prog.MandatoryMST(u, v, M, parent)
    print(result)

