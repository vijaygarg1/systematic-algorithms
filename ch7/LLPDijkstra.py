"""LLP-Dijkstra, matching bxx-sp-llp-dijkstra.tex Algorithm LLP-Dijkstra
(fig:alg-llp-dijk). The lattice points are PAIRS (fixed, G): a vertex is
settled by flipping fixed[j], and distances only ever decrease. The
predicate has two clauses -- every discovered vertex is settled, and the
shortest-path recurrence holds.

The forbidden index is the cheapest unsettled discovered vertex. The
minimality is not tested here: setContainer(PriorityQueue, G[j], min)
holds the
candidate indices in a min-heap keyed by G, so the index offered to the
guard is already the smallest one, and all that remains to check is that
it is unsettled and discovered. That is the same division of labour as
the classical Dijkstra of Algorithm Dijkstra, whose heap of (j, c) pairs
supplies the vertex and whose `if not fixed[j]` supplies the rest.

Lowering G[k] in the advance pushes k back onto the heap with its new
key, exactly as the classical algorithm does H.add(k, d[k]); the stale
entry is discarded when it surfaces and the guard rejects it. Each edge
contributes at most one push, so the whole loop runs in O(m log n).

Completeness of this cheap guard needs non-negative weights: it never
looks at settled vertices, so it is a valid detector of a violated
recurrence only while every settled vertex has had its out-edges relaxed
and carries a key no larger than any unsettled one (Lemma lem:dijk).

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
        for j in range(0, (self.n - 1) + 1):
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


