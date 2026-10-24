"""Recursive DFS recording discovery and finish times.

Auto-generated from DFSTraversal.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class DFSTraversal:
    def traverse(self, succ):
        self.succ = succ
        self.n = len(self.succ)
        self.G = [0] * (self.n)
        self.parent = [0] * (self.n)
        self.discovered = [0] * (self.n)
        self.finished = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.parent[i] = (-1)
        t = [0] * (1)
        t[0] = 1
        self.visit(0, self.succ, self.G, self.parent, self.discovered, self.finished, t)
        return self.discovered

    def visit(self, j, succ, G, parent, discovered, finished, t):
        G[j] = 1
        discovered[j] = t[0]
        t[0] = (t[0] + 1)
        for k in succ[j]:
            if (G[k] == 0):
                parent[k] = j
                self.visit(k, succ, G, parent, discovered, finished, t)
        finished[j] = t[0]
        t[0] = (t[0] + 1)


