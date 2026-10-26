"""Classical in-degree zero queue algorithm for DAG layering.

Auto-generated from Layering.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Layering:
    def layer(self, pre, succ):
        self.pre = pre
        self.succ = succ
        self.n = len(self.pre)
        self.G = [0] * (self.n)
        self.indeg = [0] * (self.n)
        for j in range(0, ((self.n - 1)) + 1):
            for i in self.pre[j]:
                self.indeg[j] = (self.indeg[j] + 1)
        Q = [0] * (self.n)
        head = 0
        tail = 0
        for j in range(0, ((self.n - 1)) + 1):
            if (self.indeg[j] == 0):
                self.G[j] = 0
                Q[tail] = j
                tail = (tail + 1)
        while (head < tail):
            j = Q[head]
            head = (head + 1)
            for k in self.succ[j]:
                self.indeg[k] = (self.indeg[k] - 1)
                if (self.indeg[k] == 0):
                    best = 0
                    for i in self.pre[k]:
                        if ((self.G[i] + 1) > best):
                            best = (self.G[i] + 1)
                    self.G[k] = best
                    Q[tail] = k
                    tail = (tail + 1)
        return self.G


