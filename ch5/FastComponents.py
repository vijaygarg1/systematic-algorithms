"""Fast connected components: SlowComponents accelerated by a
pointer-jumping clause G[j] >= G[G[j]], which doubles the effective
reach of the label-propagation step at each round and brings the
number of rounds down from O(diameter) to O(log diameter).

Auto-generated from FastComponents.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class FastComponents:
    def FastComponents(self, adj):
        self.adj = adj
        self.n = len(self.adj)
        self.G = [0] * (self.n)
        for _ix in range(0, (self.n - 1) + 1):
            self.G[_ix] = _ix
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (not (self.G[j] >= self.G[self.G[j]])):
                    self.G[j] = self.G[self.G[j]]
                    _changed = True
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (not (self.G[j] >= max((self.G[i] for i in self.adj[j]), default=-2147483647))):
                    self.G[j] = max((self.G[i] for i in self.adj[j]), default=-2147483647)
                    _changed = True
        return self.G


