"""LLP-Dijkstra, matching bxx-sp-llp-dijkstra.tex Algorithm LLP-Dijkstra
(fig:alg-llp-dijk). The lattice points are PAIRS (fixed, G): a vertex is
settled by flipping fixed[j], and distances only ever decrease. The
predicate has two clauses -- every discovered vertex is settled, and the
shortest-path recurrence holds.

Auto-generated from LLPDijkstra.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""
import heapq


class LLPDijkstra:
    def LLPDijkstra(self, adj, w):
        self.adj = adj
        self.w = w
        self.n = len(self.adj)
        self.G = [float('inf')] * (self.n)
        self.fixed = [False] * (self.n)
        self.G[0] = 0
        # setContainer: indices are offered smallest-key-first, so the
        # predicate below need not establish global minimality itself.
        _container = []
        for j in range(0, ((self.n - 1)) + 1):
            heapq.heappush(_container, (self.G[j], j))
        while _container:
            j = heapq.heappop(_container)[1]
            if (not (((not self.fixed[j]) and (self.G[j] < float('inf'))))):
                continue
            self.fixed[j] = True
            for k in self.adj[j]:
                if (not self.fixed[k]):
                    cand = (self.G[j] + self.w[j][k])
                    if (cand < self.G[k]):
                        self.G[k] = cand
                        heapq.heappush(_container, (self.G[k], k))
        return self.G


