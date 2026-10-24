"""Classical 2-SAT via Kosaraju's strongly-connected components.

Each clause (a or b) contributes the two implications !a -> b and
!b -> a, giving a graph on 2n literals. The formula is satisfiable iff
no variable shares a component with its own negation, and setting x
true exactly when x's component is numbered after !x's is a satisfying
assignment -- Kosaraju numbers components in reverse topological order,
so the later component is the one implied.

Literal x maps to index x and !x to index n + x, which is what litIndex
computes. Each literal can source up to 2m implications, so the
adjacency rows are sized 2m + 1 rather than by the literal count.

Auto-generated from TwoSAT.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class TwoSAT:
    def TwoSAT(self, clauseA, clauseB):
        self.clauseA = clauseA
        self.clauseB = clauseB
        self.m = len(self.clauseA)
        self.n = max(0, max((max(abs(self.clauseA[i]), abs(self.clauseB[i])) for i in range(0, ((self.m - 1)) + 1)), default=-2147483647))
        self.sz = ((2 * self.n) + 2)
        self.cap = ((2 * self.m) + 1)
        self.adjFwd = [[0] * (self.cap) for _ in range(self.sz)]
        self.adjRev = [[0] * (self.cap) for _ in range(self.sz)]
        self.degFwd = [0] * (self.sz)
        self.degRev = [0] * (self.sz)
        for i in range(0, ((self.m - 1)) + 1):
            self.addImplication(self.litIndex((0 - self.clauseA[i]), self.n), self.litIndex(self.clauseB[i], self.n), self.adjFwd, self.degFwd, self.adjRev, self.degRev)
            self.addImplication(self.litIndex((0 - self.clauseB[i]), self.n), self.litIndex(self.clauseA[i], self.n), self.adjFwd, self.degFwd, self.adjRev, self.degRev)
        order = [0] * (self.sz)
        orderSize = 0
        v = 0
        visited = [False] * (self.sz)
        while (v < self.sz):
            if (not visited[v]):
                orderSize = self.dfs1(v, self.adjFwd, self.degFwd, visited, order, orderSize)
            v = (v + 1)
        comp = [0] * (self.sz)
        for k in range(0, ((self.sz - 1)) + 1):
            comp[k] = (0 - 1)
        numComp = 0
        idx = (orderSize - 1)
        while (idx >= 0):
            if (comp[order[idx]] < 0):
                self.dfs2(order[idx], self.adjRev, self.degRev, comp, numComp)
                numComp = (numComp + 1)
            idx = (idx - 1)
        result = [False] * ((self.n + 1))
        for x in range(1, (self.n) + 1):
            result[x] = (comp[x] > comp[(self.n + x)])
        return result

    def addImplication(self, from_, to, adjFwd, degFwd, adjRev, degRev):
        adjFwd[from_][degFwd[from_]] = to
        degFwd[from_] = (degFwd[from_] + 1)
        adjRev[to][degRev[to]] = from_
        degRev[to] = (degRev[to] + 1)

    def dfs1(self, u, adj, deg, visited, order, orderSize):
        visited[u] = True
        for i in range(0, ((deg[u] - 1)) + 1):
            w = adj[u][i]
            if (not visited[w]):
                orderSize = self.dfs1(w, adj, deg, visited, order, orderSize)
        order[orderSize] = u
        return (orderSize + 1)

    def dfs2(self, u, adj, deg, comp, c):
        comp[u] = c
        for i in range(0, ((deg[u] - 1)) + 1):
            w = adj[u][i]
            if (comp[w] < 0):
                self.dfs2(w, adj, deg, comp, c)

    def litIndex(self, lit, n):
        if (lit > 0):
            return lit
        return (n + (0 - lit))


if __name__ == '__main__':
    clauseA = [0, 0, 1, 2]
    clauseB = [0, 0, 1, 1]
    prog = TwoSAT()
    result = prog.TwoSAT(clauseA, clauseB)
    print(result)

