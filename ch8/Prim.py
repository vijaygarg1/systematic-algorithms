"""Classical Prim MST (bxx-mst.tex, algo:prim): d[v] is the cost of adding
v to the fragment, parent[v] the fragment vertex responsible for it,
fixed[v] marks vertices already in the fragment. Same control structure
as Dijkstra (bxx-shortestPath.tex's "Comparison with Dijkstra's
algorithm" paragraph): a heap<int> keyed by d finds the minimum

Auto-generated from Prim.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""
import heapq


class Prim:
    def mst(self, adj, aw):
        self.adj = adj
        self.aw = aw
        self.n = len(self.adj)
        self.d = [0] * (self.n)
        self.parent = [0] * (self.n)
        self.fixed = [False] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.d[i] = 2147483647
            self.parent[i] = (-1)
        self.d[0] = 0
        H = []
        heapq.heappush(H, (self.d[0], 0))
        count = 0
        while (count < self.n):
            if (not H):
                return None
            v = H[0][1]
            heapq.heappop(H)
            if (not self.fixed[v]):
                self.fixed[v] = True
                count = (count + 1)
                t = 0
                while (t < len(self.adj[v])):
                    k = self.adj[v][t]
                    if ((not self.fixed[k]) and (self.aw[v][t] < self.d[k])):
                        self.d[k] = self.aw[v][t]
                        self.parent[k] = v
                        heapq.heappush(H, (self.d[k], k))
                    t = (t + 1)
        return self.parent


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    aw = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = Prim()
    result = prog.mst(adj, aw)
    print(result)

