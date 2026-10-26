"""Classical Bellman-Ford: n-1 relaxation passes over every edge.

Auto-generated from BellmanFord.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class BellmanFord:
    def shortestPath(self, n, U, V, W, s):
        self.n = n
        self.U = U
        self.V = V
        self.W = W
        self.s = s
        self.dist = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.dist[i] = 2147483647
        self.dist[self.s] = 0
        for k in range(1, ((self.n - 1)) + 1):
            for e in range(0, ((len(self.U) - 1)) + 1):
                u = self.U[e]
                v = self.V[e]
                if ((self.dist[u] != 2147483647) and ((self.dist[u] + self.W[e]) < self.dist[v])):
                    self.dist[v] = (self.dist[u] + self.W[e])
        return self.dist


if __name__ == '__main__':
    U = [0, 0, 1, 2]
    V = [0, 0, 1, 1]
    W = [0, 0, 0, 2]
    prog = BellmanFord()
    result = prog.shortestPath(len(U), U, V, W, 0)
    print(result)

