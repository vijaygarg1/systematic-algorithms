"""LLP-Dijkstra: forbidden when j is not yet fixed and its distance is
finite; the advance fixes j and relaxes every edge out of it. The
setContainer line gives the LLP loop a priority queue keyed on G[j],
so the forbidden index chosen is always the closest unfixed vertex --
which is exactly Dijkstra's greedy choice.

The book writes the weight as w(i,j), with parentheses, to say that it
comes from the edge and not from an n x n array -- an array would cost
Theta(n^2) to build before Dijkstra's own O(m log n) could start. So the
edges arrive as two parallel ragged arrays: adj[j] lists j's successors
and aw[j][t] is the weight of the edge j -> adj[j][t]. Both are O(m).
Dijkstra.llp and LLPJohnson.llp use the same pair.

Auto-generated from LLPDijkstra.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""
import heapq


class LLPDijkstra:
    def LLPDijkstra(self, adj, aw):
        self.adj = adj
        self.aw = aw
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
            for t in range(0, ((len(self.adj[j]) - 1)) + 1):
                k = self.adj[j][t]
                if (not self.fixed[k]):
                    cand = (self.G[j] + self.aw[j][t])
                    if (cand < self.G[k]):
                        self.G[k] = cand
                        heapq.heappush(_container, (self.G[k], k))
        return self.G


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    aw = [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0], [9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0], [17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0], [25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0], [33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0], [41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0], [49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0], [57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0]]
    prog = LLPDijkstra()
    result = prog.LLPDijkstra(adj, aw)
    print(result)

