"""Classical Boruvka minimum spanning tree: in each round every current
component picks its own cheapest outgoing edge and all those edges are
added at once, so the number of components at least halves per round
and O(log n) rounds suffice.

T marks the chosen edges; cid[v] is the representative of v's component,
recomputed each round by flooding the edges already in T. A round that
adds no edge means the graph is disconnected, which is what progress
stops on -- otherwise the loop would spin forever below n-1 edges.

Auto-generated from Boruvka.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Boruvka:
    def Boruvka(self, n, U, V, W):
        self.n = n
        self.U = U
        self.V = V
        self.W = W
        self.m = len(self.U)
        self.treeEdges = 0
        self.T = [False] * (self.m)
        self.cid = [0] * (self.n)
        self.progress = True
        while ((self.treeEdges < (self.n - 1)) and self.progress):
            roundStart = self.treeEdges
            self.components(self.n, self.U, self.V, self.T, self.cid)
            mwe = [0] * (self.n)
            dist = [0.0] * (self.n)
            for i in range(0, ((self.n - 1)) + 1):
                mwe[i] = (-1)
            for i in range(0, ((self.n - 1)) + 1):
                dist[i] = float('inf')
            for e in range(0, ((self.m - 1)) + 1):
                if (self.cid[self.U[e]] != self.cid[self.V[e]]):
                    self.relax(self.cid[self.U[e]], e, self.W, dist, mwe)
                    self.relax(self.cid[self.V[e]], e, self.W, dist, mwe)
            for i in range(0, ((self.n - 1)) + 1):
                if (((self.cid[i] == i) and (mwe[i] != (-1))) and (not self.T[mwe[i]])):
                    self.T[mwe[i]] = True
                    self.treeEdges = (self.treeEdges + 1)
            self.progress = (self.treeEdges > roundStart)
        return self.T

    def relax(self, comp, e, W, dist, mwe):
        if (W[e] < dist[comp]):
            dist[comp] = W[e]
            mwe[comp] = e

    def components(self, n, U, V, T, cid):
        visited = [False] * (n)
        deg = [0] * (n)
        fill = [0] * (n)
        tadj = [None] * (n)
        for e in range(0, ((len(U) - 1)) + 1):
            if T[e]:
                deg[U[e]] = (deg[U[e]] + 1)
                deg[V[e]] = (deg[V[e]] + 1)
        for i in range(0, ((n - 1)) + 1):
            tadj[i] = [0] * (deg[i])
        for e in range(0, ((len(U) - 1)) + 1):
            if T[e]:
                tadj[U[e]][fill[U[e]]] = V[e]
                fill[U[e]] = (fill[U[e]] + 1)
                tadj[V[e]][fill[V[e]]] = U[e]
                fill[V[e]] = (fill[V[e]] + 1)
        for i in range(0, ((n - 1)) + 1):
            if (not visited[i]):
                self.bfs(i, i, visited, cid, tadj)

    def bfs(self, v, root, visited, cid, tadj):
        visited[v] = True
        cid[v] = root
        for q in range(0, ((len(tadj[v]) - 1)) + 1):
            u = tadj[v][q]
            if (not visited[u]):
                self.bfs(u, root, visited, cid, tadj)

    def neighborIf(self, a, b, v):
        if (a == v):
            return b
        if (b == v):
            return a
        return (-1)


if __name__ == '__main__':
    U = [0, 0, 1, 2]
    V = [0, 0, 1, 1]
    W = [0, 0, 0, 2]
    prog = Boruvka()
    result = prog.Boruvka(len(U), U, V, W)
    print(result)

