"""Classical Bellman-Ford, as bxx-shortestPath.tex's Algorithm BellmanFord
states it:

forall v in V: d[v] := infinity; p[v] := null
d[s] := 0
for k = 1 to n-1:
forall edge (u,v) in E:
if d[u] + w[u,v] < d[v]: d[v] := d[u] + w[u,v];  p[v] := u
forall edge (u,v) in E:
if d[u] + w[u,v] < d[v]: return "Negative cycle detected"
return d[], p[]

Edges arrive as three parallel arrays: edge e runs U[e] -> V[e] with
weight W[e]. That is the book's "forall edge (u,v) in E" written out.

p[v] = -1 stands for the book's null: -1 is not a vertex, since vertices
are 0..n-1. The book declares p as a second output; LL returns one value,
so d is returned and p is computed alongside it.

A negative cycle makes the program return null, LL's "no solution".

d[u] != infinity guards the relaxation: the book's d[u] + w[u,v] is
infinity + w = infinity when u is unreached, which is never < d[v]. In
finite ints infinity + w overflows to a large negative instead, so the
test has to be made explicitly.

The k loop and the edge loops are `for`, not `forall`: each pass reads
the distances the previous pass wrote.

Auto-generated from BellmanFord.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class BellmanFord:
    def BellmanFord(self, n, U, V, W, s):
        self.n = n
        self.U = U
        self.V = V
        self.W = W
        self.s = s
        self.d = [0] * (self.n)
        self.p = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.d[i] = 2147483647
        for i in range(0, ((self.n - 1)) + 1):
            self.p[i] = (-1)
        self.d[self.s] = 0
        for k in range(1, ((self.n - 1)) + 1):
            for e in range(0, ((len(self.U) - 1)) + 1):
                u = self.U[e]
                v = self.V[e]
                if ((self.d[u] != 2147483647) and ((self.d[u] + self.W[e]) < self.d[v])):
                    self.d[v] = (self.d[u] + self.W[e])
                    self.p[v] = u
        for e in range(0, ((len(self.U) - 1)) + 1):
            u = self.U[e]
            v = self.V[e]
            if ((self.d[u] != 2147483647) and ((self.d[u] + self.W[e]) < self.d[v])):
                return None
        return self.d


if __name__ == '__main__':
    U = [0, 0, 1, 2]
    V = [0, 0, 1, 1]
    W = [0, 0, 0, 2]
    prog = BellmanFord()
    result = prog.BellmanFord(len(U), U, V, W, 0)
    print(result)

