"""Classical Edmonds-Karp maximum flow: Ford-Fulkerson with the augmenting
path chosen by BREADTH-first search, so each augmentation uses a path
with the fewest arcs. That single change is what bounds the number of
augmentations by O(VE) and makes the running time independent of the
capacities.

f is the flow matrix; residual(u, v) = c[u][v] - f[u][v] is the spare
capacity of arc (u,v), and pushing b along (u,v) also subtracts b from
f[v][u], which is what lets a later path cancel earlier flow. The BFS
carries its own queue q with head/tail indices and leaves the path in
parent[], as a chain of predecessors running back from t to s.

Auto-generated from EdmondsKarp.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class EdmondsKarp:
    def maxflow(self, c, s, t):
        self.c = c
        self.s = s
        self.t = t
        self.n = len(self.c)
        self.f = [[0] * (self.n) for _ in range(self.n)]
        self.parent = [0] * (self.n)
        while (self.findAugmentingPath(self.c, self.f, self.s, self.t, self.parent) == 1):
            self.augment(self.f, self.s, self.t, self.parent, self.bottleneck(self.c, self.f, self.s, self.t, self.parent))
        return self.f

    def bottleneck(self, c, f, s, v, parent):
        if (v == s):
            return 2147483647
        return min(self.residual(c, f, parent[v], v), self.bottleneck(c, f, s, parent[v], parent))

    def augment(self, f, s, v, parent, b):
        if (v == s):
            return
        u = parent[v]
        f[u][v] = (f[u][v] + b)
        f[v][u] = (f[v][u] - b)
        self.augment(f, s, u, parent, b)

    def findAugmentingPath(self, c, f, s, t, parent):
        seen = [False] * (len(c))
        q = [0] * (len(c))
        q[0] = s
        seen[s] = True
        parent[s] = s
        return self.bfs(c, f, t, parent, seen, q, 0, 1)

    def bfs(self, c, f, t, parent, seen, q, head, tail):
        if (head >= tail):
            return 0
        u = q[head]
        if (u == t):
            return 1
        for v in range(0, ((len(c) - 1)) + 1):
            if ((not seen[v]) and (self.residual(c, f, u, v) > 0)):
                seen[v] = True
                parent[v] = u
                q[tail] = v
                tail = (tail + 1)
        return self.bfs(c, f, t, parent, seen, q, (head + 1), tail)

    def residual(self, c, f, u, v):
        return (c[u][v] - f[u][v])


if __name__ == '__main__':
    c = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = EdmondsKarp()
    result = prog.maxflow(c, 0, 1)
    print(result)

