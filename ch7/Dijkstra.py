"""Classical Dijkstra (bxx-shortestPath.tex, fig:dijk): binary min-heap
H of (vertex, cost) pairs keyed by cost, with lazy deletion -- a
vertex may be pushed more than once as its distance improves; stale
entries are simply skipped on removal via the fixed[] check -- all
exactly matching the book's H.add()/H.removeMin() algorithm, not a
linear O(n) scan for the minimum each round. Uses the heap<int>
builtin (-> java.util.PriorityQueue<int[]>, keyed by cost, carrying
vertex as the payload) instead of hand-rolled heapPush/heapPop/
heapify, matching the hand-written Python (heapq) and Rust
(BinaryHeap) versions of this same program, which already lean on
their standard library's heap.

The graph is a weighted adjacency list: adj[j] holds the out-neighbours
of j and the parallel aw[j] holds their edge weights, so the weight of
(j, adj[j][t]) is aw[j][t]. There is no n x n matrix anywhere, so the
whole structure is O(n + m) rather than Theta(n^2).

The relaxation loop walks adj[j], which is the book's
"forall k: not fixed[k] and (j,k) in E". That is what makes the
algorithm O(m log n): each edge is relaxed once and contributes at most
one heap push. Scanning all n vertices instead -- reading weights out
of a dense matrix -- costs Theta(n^2) however few edges the graph has,
and so cannot meet the book's bound on a sparse graph.

Auto-generated from Dijkstra.llp by lang/llcpy.py.
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


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    aw = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = Dijkstra()
    result = prog.Dijkstra(adj, aw, 0)
    print(result)

