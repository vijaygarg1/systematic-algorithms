"""Sequential BFS distance from a source vertex (FIFO queue).

Auto-generated from BFSTraversal.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class BFSTraversal:
    def traverse(self, succ, s):
        self.succ = succ
        self.s = s
        self.n = len(self.succ)
        self.G = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.G[i] = 2147483647
        self.G[self.s] = 0
        Q = [0] * (self.n)
        head = 0
        tail = 0
        Q[tail] = self.s
        tail = (tail + 1)
        while (head < tail):
            j = Q[head]
            head = (head + 1)
            for k in self.succ[j]:
                if (self.G[k] > (self.G[j] + 1)):
                    self.G[k] = (self.G[j] + 1)
                    Q[tail] = k
                    tail = (tail + 1)
        return self.G


