"""Classical Johnson all-pairs shortest paths: BellmanFord computes a
non-negative price vector that reweights edges, then Dijkstra runs from
every source on the reweighted graph. Returns null on negative cycle.

Auto-generated from Johnson.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""
import heapq


class Dijkstra:
    def shortestPath(self, adj, aw, s):
        self.adj = adj
        self.aw = aw
        self.s = s
        self.n = len(self.adj)
        self.INF = 2147483647
        self.dist = [0] * (self.n)
        self.fixed = [False] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.dist[i] = self.INF
        self.dist[self.s] = 0
        H = []
        heapq.heappush(H, (self.dist[self.s], self.s))
        while (not (not H)):
            j = H[0][1]
            heapq.heappop(H)
            if (not self.fixed[j]):
                self.fixed[j] = True
                t = 0
                while (t < len(self.adj[j])):
                    k = self.adj[j][t]
                    if ((not self.fixed[k]) and ((self.dist[j] + self.aw[j][t]) < self.dist[k])):
                        self.dist[k] = (self.dist[j] + self.aw[j][t])
                        heapq.heappush(H, (self.dist[k], k))
                    t = (t + 1)
        return self.dist


class Johnson:
    def allPairs(self, adj, aw):
        self.adj = adj
        self.aw = aw
        self.n = len(self.adj)
        self.INF = 2147483647
        self.dist = [0] * (self.n)
        self.k = 0
        while (self.k < (self.n - 1)):
            changed = False
            for i in range(0, ((self.n - 1)) + 1):
                t = 0
                while (t < len(self.adj[i])):
                    j = self.adj[i][t]
                    if ((self.dist[i] + self.aw[i][t]) < self.dist[j]):
                        self.dist[j] = (self.dist[i] + self.aw[i][t])
                        changed = True
                    t = (t + 1)
            if changed:
                self.k = (self.k + 1)
            else:
                self.k = self.n
        negCycle = False
        for i in range(0, ((self.n - 1)) + 1):
            t = 0
            while (t < len(self.adj[i])):
                if ((self.dist[i] + self.aw[i][t]) < self.dist[self.adj[i][t]]):
                    negCycle = True
                t = (t + 1)
        if negCycle:
            return None
        price = [0] * (self.n)
        for v in range(0, ((self.n - 1)) + 1):
            price[v] = (0 - self.dist[v])
        awPrime = [None] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            awPrime[i] = [0] * (len(self.adj[i]))
        for i in range(0, ((self.n - 1)) + 1):
            t = 0
            while (t < len(self.adj[i])):
                awPrime[i][t] = self.reweight(self.aw[i][t], price[self.adj[i][t]], price[i])
                t = (t + 1)
        D = [[0] * (self.n) for _ in range(self.n)]
        for u in range(0, ((self.n - 1)) + 1):
            distPrime = Dijkstra().shortestPath(self.adj, awPrime, u)
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
    result = prog.allPairs(adj, aw)
    print(result)

