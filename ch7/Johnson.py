"""Classical Johnson all-pairs shortest paths: BellmanFord computes a
non-negative price vector that reweights edges, then Dijkstra runs from
every source on the reweighted graph. Returns null on negative cycle.

Calls the sibling Dijkstra program (`Dijkstra(adj, wPrime, u)`, compiling
to `new Dijkstra().shortestPath(adj, wPrime, u)`) instead of keeping its own
copy of the single-source relaxation loop -- confirmed correct against
the standard CLRS Johnson's-algorithm worked example. This makes
Johnson.java a multi-file program: it requires Dijkstra.java alongside
it to compile (validate-site.py's Java check and the website's
download links both know about this dependency; see JAVA_MULTI_FILE
in validate-site.py and the `javaRequires` demo-spec field in llp.js).

The book's box says "Run BellmanFord" and "Run Dijkstra" symmetrically,
but only Dijkstra is called here; step 1 keeps its own relaxation loop.
That is a capability gap, not an oversight. Sibling BellmanFord.llp
takes an edge list -- shortestPath(n, U, V, W, s) -- where Johnson has
an adjacency matrix, and it runs its n-1 passes and returns dist with
no extra pass and no way to report a negative cycle. Johnson needs
exactly that report, so calling it would drop the check the book names
explicitly.

The graph is a weighted adjacency list: adj[i] holds the out-neighbours
of i and the parallel aw[i] their edge weights, so the weight of
(i, adj[i][t]) is aw[i][t]. The reweighted graph is stored the same way,
in awPrime, which shares adj since reweighting changes no edge -- so the
only Theta(n^2) object left is the all-pairs output D itself, which is
Theta(n^2) by definition.

Both relaxation loops walk adj[i] rather than all n candidate heads.
That is what gives the book's O(nm log n): step 1 is n-1 passes over m
edges, and step 4 is n Dijkstra runs of O(m log n) each. Scanning all
pairs instead makes step 1 Theta(n^3) on its own, which already exceeds
the bound on any sparse graph.

No edge weight is ever INF here: every pair in adj is a real edge, so
the "w[i][j] < INF" guards the matrix version needed are gone.

Auto-generated from Johnson.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""
import heapq


class Dijkstra:
    def Dijkstra(self, adj, aw, s):
        self.adj = adj
        self.aw = aw
        self.s = s
        self.n = len(self.adj)
        self.d = [0] * (self.n)
        self.fixed = [False] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.d[i] = 2147483647
        self.d[self.s] = 0
        H = []
        heapq.heappush(H, (self.d[self.s], self.s))
        while (not (not H)):
            j = H[0][1]
            heapq.heappop(H)
            if (not self.fixed[j]):
                self.fixed[j] = True
                for t in range(0, ((len(self.adj[j]) - 1)) + 1):
                    k = self.adj[j][t]
                    if ((not self.fixed[k]) and ((self.d[j] + self.aw[j][t]) < self.d[k])):
                        self.d[k] = (self.d[j] + self.aw[j][t])
                        heapq.heappush(H, (self.d[k], k))
        return self.d


class Johnson:
    def Johnson(self, adj, aw):
        self.adj = adj
        self.aw = aw
        self.n = len(self.adj)
        self.INF = 2147483647
        self.dist = [0] * (self.n)
        self.k = 0
        while (self.k < (self.n - 1)):
            changed = False
            for i in range(0, ((self.n - 1)) + 1):
                for t in range(0, ((len(self.adj[i]) - 1)) + 1):
                    j = self.adj[i][t]
                    if ((self.dist[i] + self.aw[i][t]) < self.dist[j]):
                        self.dist[j] = (self.dist[i] + self.aw[i][t])
                        changed = True
            if changed:
                self.k = (self.k + 1)
            else:
                self.k = self.n
        negCycle = False
        for i in range(0, ((self.n - 1)) + 1):
            for t in range(0, ((len(self.adj[i]) - 1)) + 1):
                if ((self.dist[i] + self.aw[i][t]) < self.dist[self.adj[i][t]]):
                    negCycle = True
        if negCycle:
            return None
        price = [0] * (self.n)
        for v in range(0, ((self.n - 1)) + 1):
            price[v] = (0 - self.dist[v])
        awPrime = [None] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            awPrime[i] = [0] * (len(self.adj[i]))
        for i in range(0, ((self.n - 1)) + 1):
            for t in range(0, ((len(self.adj[i]) - 1)) + 1):
                awPrime[i][t] = self.reweight(self.aw[i][t], price[self.adj[i][t]], price[i])
        D = [[0] * (self.n) for _ in range(self.n)]
        for u in range(0, ((self.n - 1)) + 1):
            distPrime = Dijkstra().Dijkstra(self.adj, awPrime, u)
            for v in range(0, ((self.n - 1)) + 1):
                D[u][v] = self.reweight(distPrime[v], price[u], price[v])
        return D

    def reweight(self, len_, plus, minus):
        if (len_ >= 2147483647):
            return 2147483647
        return ((len_ + plus) - minus)


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    aw = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = Johnson()
    result = prog.Johnson(adj, aw)
    print(result)

