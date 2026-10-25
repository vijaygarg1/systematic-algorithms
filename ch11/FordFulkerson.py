"""Classical Ford-Fulkerson maximum flow: repeatedly find an augmenting
path in the residual graph by depth-first search and push the largest
amount that fits along it, until no augmenting path remains.

REPRESENTATION. The graph arrives as an edge list -- U[i], V[i], C[i]
for edge i -- and is turned into the standard PAIRED-ARC structure:
input edge i becomes arcs 2i (forward, capacity C[i]) and 2i+1
(reverse, capacity 0), adj[u] holds the ARC INDICES leaving u, and
head[e] is the arc's head. The reverse of arc e is
rev(e) = e + 1 - 2 * (e % 2)
(LL has no ^, so the usual e^1 is spelled out), which is what makes
"cancel the reverse arc first" an O(1) step.

This replaces a capacity/flow MATRIX. The book's bound is O(f* m): each
augmenting-path search is O(m), because it walks the edges actually
present. A matrix forces every search to scan all n candidate heads per
vertex -- Theta(n^2) per search no matter how few edges there are --
which makes the program O(f* n^2) and matches the book only on a dense
graph. The book writes f(u,v) and c_f(u,v) throughout, never f[u,v]:
a function of an edge, not a matrix lookup.

FLOW CONVENTION. flow stays NON-NEGATIVE -- a flow function, not an
antisymmetric skew matrix -- which is the book's own convention: its
inner loop reads `x := min(delta, f(v,u))`, cancels that much reverse
flow first, and only puts the remainder on (u,v). The matching residual
capacity is therefore
residual(e) = cap[e] - flow[e] + flow[rev(e)].

The returned array is indexed by INPUT edge: result[i] is the flow on
edge i, which is flow on arc 2i.

Auto-generated from FordFulkerson.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class FordFulkerson:
    def FordFulkerson(self, n, U, V, C, s, t):
        self.n = n
        self.U = U
        self.V = V
        self.C = C
        self.s = s
        self.t = t
        self.m = len(self.U)
        self.head = [0] * ((2 * self.m))
        self.cap = [0] * ((2 * self.m))
        self.flow = [0] * ((2 * self.m))
        for i in range(0, ((self.m - 1)) + 1):
            self.head[(2 * i)] = self.V[i]
            self.cap[(2 * i)] = self.C[i]
            self.head[((2 * i) + 1)] = self.U[i]
            self.cap[((2 * i) + 1)] = 0
        deg = [0] * (self.n)
        for i in range(0, ((self.m - 1)) + 1):
            deg[self.U[i]] = (deg[self.U[i]] + 1)
            deg[self.V[i]] = (deg[self.V[i]] + 1)
        adj = [None] * (self.n)
        for u in range(0, ((self.n - 1)) + 1):
            adj[u] = [0] * (deg[u])
        fill = [0] * (self.n)
        for i in range(0, ((self.m - 1)) + 1):
            adj[self.U[i]][fill[self.U[i]]] = (2 * i)
            fill[self.U[i]] = (fill[self.U[i]] + 1)
            adj[self.V[i]][fill[self.V[i]]] = ((2 * i) + 1)
            fill[self.V[i]] = (fill[self.V[i]] + 1)
        parentArc = [0] * (self.n)
        while (self.findAugmentingPath(self.n, adj, self.head, self.cap, self.flow, self.s, self.t, parentArc) == 1):
            self.augment(self.head, self.cap, self.flow, self.s, self.t, parentArc, self.bottleneck(self.head, self.cap, self.flow, self.s, self.t, parentArc))
        result = [0] * (self.m)
        for i in range(0, ((self.m - 1)) + 1):
            result[i] = self.flow[(2 * i)]
        return result

    def rev(self, e):
        return ((e + 1) - (2 * (e % 2)))

    def residual(self, cap, flow, e):
        return ((cap[e] - flow[e]) + flow[self.rev(e)])

    def bottleneck(self, head, cap, flow, s, v, parentArc):
        if (v == s):
            return 2147483647
        e = parentArc[v]
        return min(self.residual(cap, flow, e), self.bottleneck(head, cap, flow, s, head[self.rev(e)], parentArc))

    def augment(self, head, cap, flow, s, v, parentArc, b):
        if (v == s):
            return
        e = parentArc[v]
        x = min(b, flow[self.rev(e)])
        if (x > 0):
            flow[self.rev(e)] = (flow[self.rev(e)] - x)
        if (b > x):
            flow[e] = (flow[e] + (b - x))
        self.augment(head, cap, flow, s, head[self.rev(e)], parentArc, b)

    def findAugmentingPath(self, n, adj, head, cap, flow, s, t, parentArc):
        seen = [False] * (n)
        seen[s] = True
        return self.dfs(adj, head, cap, flow, s, t, parentArc, seen)

    def dfs(self, adj, head, cap, flow, u, t, parentArc, seen):
        if (u == t):
            return 1
        for q in range(0, ((len(adj[u]) - 1)) + 1):
            e = adj[u][q]
            v = head[e]
            if ((not seen[v]) and (self.residual(cap, flow, e) > 0)):
                seen[v] = True
                parentArc[v] = e
                if (self.dfs(adj, head, cap, flow, v, t, parentArc, seen) == 1):
                    return 1
        return 0


if __name__ == '__main__':
    U = [0, 0, 1, 2]
    V = [0, 0, 1, 1]
    C = [0, 0, 0, 2]
    prog = FordFulkerson()
    result = prog.FordFulkerson(len(U), U, V, C, 0, 1)
    print(result)

