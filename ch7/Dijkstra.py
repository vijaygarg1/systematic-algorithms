"""Classical Dijkstra (bxx-shortestPath.tex, fig:dijk): binary min-heap
H of (vertex, cost) pairs keyed by cost, with lazy deletion -- a
vertex may be pushed more than once as its distance improves; stale
entries are simply skipped on removal via the fixed[] check -- all
exactly matching the book's H.add()/H.removeMin() algorithm, not a

Auto-generated from Dijkstra.llp by lang/llcpy.py.
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


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    aw = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = Dijkstra()
    result = prog.shortestPath(adj, aw, 0)
    print(result)

