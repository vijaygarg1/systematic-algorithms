"""Classical Kruskal MST: sort edges, union-find with rank and path-compression.
Calls the sibling UnionFind program (`UnionFind.Union(parent, rank, u, v)`,
compiling to `new UnionFind().union(parent, rank, u, v)`) instead of
keeping its own copy of find/union-by-rank -- UnionFind.Union() already
finds both roots internally and reports whether they were distinct, so
Kruskal's main loop needs no separate find() call of its own. This makes
Kruskal.java a multi-file program: it requires UnionFind.java alongside
it to compile (see JAVA_MULTI_FILE in validate-site.py and the
`javaRequires` demo-spec field in llp.js).

Auto-generated from Kruskal.llp by lang/llcpy.py.
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


class Kruskal:
    def Kruskal(self, n, U, V, W):
        self.n = n
        self.U = U
        self.V = V
        self.W = W
        self.m = len(self.U)
        self.T = [False] * (self.m)
        self.parent = [0] * (self.n)
        self.rank = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.parent[i] = i
        chosen = 0
        e = 0
        while ((e < self.m) and (chosen < (self.n - 1))):
            if UnionFind().Union(self.parent, self.rank, self.U[e], self.V[e]):
                self.T[e] = True
                chosen = (chosen + 1)
            e = (e + 1)
        return self.T


if __name__ == '__main__':
    U = [0, 0, 1, 2]
    V = [0, 0, 1, 1]
    W = [0, 0, 0, 2]
    prog = Kruskal()
    result = prog.Kruskal(len(U), U, V, W)
    print(result)

