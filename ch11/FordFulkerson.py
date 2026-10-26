"""Classical Ford-Fulkerson maximum flow: repeatedly find an augmenting
path in the residual graph by depth-first search and push the largest
amount that fits along it, until no augmenting path remains.

Auto-generated from FordFulkerson.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class FordFulkerson:
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
        seen[s] = True
        parent[s] = s
        return self.dfs(c, f, s, t, parent, seen)

    def dfs(self, c, f, u, t, parent, seen):
        if (u == t):
            return 1
        for v in range(0, ((len(c) - 1)) + 1):
            if ((not seen[v]) and (self.residual(c, f, u, v) > 0)):
                seen[v] = True
                parent[v] = u
                if (self.dfs(c, f, v, t, parent, seen) == 1):
                    return 1
        return 0

    def residual(self, c, f, u, v):
        return (c[u][v] - f[u][v])


if __name__ == '__main__':
    c = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = FordFulkerson()
    result = prog.maxflow(c, 0, 1)
    print(result)

