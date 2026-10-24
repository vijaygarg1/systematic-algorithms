"""LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation.
The advance leaves G[0] alone -- the source distance is pinned at 0 -- and
recomputes every other G[k] from its predecessors, as in the book's box.

Auto-generated from LLPBellmanFord.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPBellmanFord:
    def LLPBellmanFord(self, pre, w):
        self.pre = pre
        self.w = w
        self.n = len(self.pre)
        self.G = [float('inf')] * (self.n)
        self.G[0] = 0
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if any((self.G[j] > (self.G[i] + self.w[i][j])) for i in self.pre[j]):
                    for k in range(1, ((self.n - 1)) + 1):
                        self.G[k] = min(((self.G[i] + self.w[i][k]) for i in self.pre[k]), default=float('inf'))
                    _changed = True
        return self.G


