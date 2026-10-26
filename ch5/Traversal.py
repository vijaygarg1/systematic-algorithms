"""Generic queue-based reachability from vertex 0.

Auto-generated from Traversal.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Traversal:
    def reach(self, succ):
        self.succ = succ
        self.n = len(self.succ)
        self.G = [0] * (self.n)
        self.Q = [0] * (self.n)
        self.head = 0
        self.tail = 0
        self.G[0] = 1
        self.Q[self.tail] = 0
        self.tail = (self.tail + 1)
        while (self.head < self.tail):
            j = self.Q[self.head]
            self.head = (self.head + 1)
            for k in self.succ[j]:
                if (self.G[k] == 0):
                    self.G[k] = 1
                    self.Q[self.tail] = k
                    self.tail = (self.tail + 1)
        return self.G


