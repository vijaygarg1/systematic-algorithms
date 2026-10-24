"""LLP-WeightedVertexCover: primal-dual 2-approximation for weighted
vertex cover, matching bxx-approximate.tex Algorithm
LLP-WeightedVertexCover (fig:llp-wvc). The lattice is indexed by EDGES,
as the book's "G: array[E] of real" and "Forbidden(e)" say: G[e] is the
price on edge e. A vertex v is tight when its incident prices saturate
w[v]; an edge is slack when neither endpoint is tight; slackMin(e)
further requires e to be lex-minimal among every slack edge sharing an
endpoint with it. Advancing raises the edge's price by the smaller of
its two endpoints' remaining budgets, tightening at least one.

The graph arrives as an edge list -- eu[e], ev[e] are the endpoints of
edge e, with the edges in lex order -- which is the book's
"E: set of edges". inc[v] lists the edges incident to v, built once from
that list in O(n + m). No n x n structure appears anywhere: the earlier
version indexed both the lattice (G[u][v]) and the graph (adj[u][v]) by
vertex PAIRS, which cost Theta(n^2) in memory and, because the forbidden
clause ranged over pairs, made the rescan enumerate all n^2 of them in
every sweep however few edges the graph had. Indexing by edge makes a
sweep O(m).

Two things keep the predicate cheap. First, price[] is maintained by the
advance instead of being re-summed on every call, so tight() is O(1)
rather than an O(nv) sum. Second, the lex-minimality test does not scan
all edge pairs: with the edges in lex order, the lex-least slack edge at
a vertex is simply the first slack one in inc[v], so e is lex-minimal
among the slack edges touching it exactly when it is first at both ends.
minSlackEdge[] is rebuilt only after an advance has moved a price, and a
rebuild consults each vertex's own incident edges only, so it is O(m)
rather than O(n^2).

Auto-generated from LLPWeightedVertexCover.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPWeightedVertexCover:
    def LLPWeightedVertexCover(self, eu, ev, w):
        self.eu = eu
        self.ev = ev
        self.w = w
        self.n = len(self.eu)
        self.nv = len(self.w)
        self.m = len(self.eu)
        self.G = [0.0] * (self.m)
        self.price = [0.0] * (self.nv)
        self.minSlackEdge = [0] * (self.nv)
        self.edeg = [0] * (self.nv)
        self.efill = [0] * (self.nv)
        self.inc = [None] * (self.nv)
        self.fresh = False
        for e in range(0, ((self.m - 1)) + 1):
            self.edeg[self.eu[e]] = (self.edeg[self.eu[e]] + 1)
            self.edeg[self.ev[e]] = (self.edeg[self.ev[e]] + 1)
        for v in range(0, ((self.nv - 1)) + 1):
            self.inc[v] = [0] * (self.edeg[v])
        for e in range(0, ((self.m - 1)) + 1):
            self.inc[self.eu[e]][self.efill[self.eu[e]]] = e
            self.efill[self.eu[e]] = (self.efill[self.eu[e]] + 1)
            self.inc[self.ev[e]][self.efill[self.ev[e]]] = e
            self.efill[self.ev[e]] = (self.efill[self.ev[e]] + 1)
        _changed = True
        while _changed:
            _changed = False
            for e in range(0, (self.n - 1) + 1):
                if self.slackMin(e):
                    step = min((self.w[self.eu[e]] - self.price[self.eu[e]]), (self.w[self.ev[e]] - self.price[self.ev[e]]))
                    self.G[e] = (self.G[e] + step)
                    self.price[self.eu[e]] = (self.price[self.eu[e]] + step)
                    self.price[self.ev[e]] = (self.price[self.ev[e]] + step)
                    self.fresh = False
                    _changed = True
        C = [False] * (self.nv)
        for i in range(0, ((self.nv - 1)) + 1):
            C[i] = self.tight(i)
        return C

    def tight(self, v):
        return (self.price[v] >= self.w[v])

    def slack(self, e):
        return ((not self.tight(self.eu[e])) and (not self.tight(self.ev[e])))

    def refresh(self):
        if (not self.fresh):
            for v in range(0, ((self.nv - 1)) + 1):
                self.minSlackEdge[v] = self.firstSlackEdge(v)
            self.fresh = True

    def firstSlackEdge(self, v):
        k = 0
        while (k < len(self.inc[v])):
            if self.slack(self.inc[v][k]):
                return self.inc[v][k]
            k = (k + 1)
        return (0 - 1)

    def slackMin(self, e):
        self.refresh()
        if (not self.slack(e)):
            return False
        return ((self.minSlackEdge[self.eu[e]] == e) and (self.minSlackEdge[self.ev[e]] == e))


if __name__ == '__main__':
    eu = [0, 0, 1, 2]
    ev = [0, 0, 1, 1]
    w = [1.0, 2.0, 3.0, 4.0]
    prog = LLPWeightedVertexCover()
    result = prog.LLPWeightedVertexCover(eu, ev, w)
    print(result)

