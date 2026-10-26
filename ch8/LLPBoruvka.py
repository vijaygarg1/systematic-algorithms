"""LLP-Boruvka: Boruvka's minimum spanning tree with the component
contraction done by LLP pointer jumping instead of recursion.

Auto-generated from LLPBoruvka.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPBoruvka:
    def mst(self, n, U, V, W):
        self.n = n
        self.U = U
        self.V = V
        self.W = W
        self.T = [False] * (len(self.U))
        self.active = [False] * (self.n)
        self.G = [0] * (self.n)
        self.mweEdge = [0] * (self.n)
        self.mweW = [0.0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.G[i] = i
            self.active[i] = True
        progress = True
        while ((self.countActive() > 1) and progress):
            before = self.countActive()
            for i in range(0, ((self.n - 1)) + 1):
                self.mweEdge[i] = (0 - 1)
                self.mweW[i] = float('inf')
            for e in range(0, ((len(self.U) - 1)) + 1):
                ru = self.find(self.U[e])
                rv = self.find(self.V[e])
                if ((self.active[ru] and self.active[rv]) and (ru != rv)):
                    self.relax(ru, e, self.W)
                    self.relax(rv, e, self.W)
            for i in range(0, ((self.n - 1)) + 1):
                if self.active[i]:
                    self.G[i] = self.pointTo(i)
            _changed = True
            while _changed:
                _changed = False
                for j in range(0, ((self.n - 1)) + 1):
                    if (self.active[j] and (self.G[j] != self.G[self.G[j]])):
                        self.G[j] = self.G[self.G[j]]
                        _changed = True
            for i in range(0, ((self.n - 1)) + 1):
                if (self.active[i] and (self.mweEdge[i] != (0 - 1))):
                    self.T[self.mweEdge[i]] = True
            for i in range(0, ((self.n - 1)) + 1):
                if (self.active[i] and (self.G[i] != i)):
                    self.active[i] = False
            progress = (self.countActive() < before)
        return self.T

    def relax(self, comp, e, W):
        if (W[e] < self.mweW[comp]):
            self.mweW[comp] = W[e]
            self.mweEdge[comp] = e

    def pointTo(self, i):
        if (self.mweEdge[i] == (0 - 1)):
            return i
        w = self.otherEndpoint(self.mweEdge[i], i)
        wEdge = self.mweEdge[w]
        if (((wEdge != (0 - 1)) and (self.otherEndpoint(wEdge, w) == i)) and (i < w)):
            return i
        return w

    def find(self, v):
        while (self.G[v] != v):
            v = self.G[v]
        return v

    def countActive(self):
        return sum(1 for i in range(0, ((self.n - 1)) + 1) if self.active[i])

    def otherEndpoint(self, e, v):
        if (self.find(self.U[e]) == v):
            return self.find(self.V[e])
        return self.find(self.U[e])


if __name__ == '__main__':
    U = [0, 0, 1, 2]
    V = [0, 0, 1, 1]
    W = [0, 0, 0, 2]
    prog = LLPBoruvka()
    result = prog.mst(len(U), U, V, W)
    print(result)

