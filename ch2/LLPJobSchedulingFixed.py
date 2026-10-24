"""LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed.

Auto-generated from LLPJobSchedulingFixed.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPJobSchedulingFixed:
    def LLPJobSchedulingFixed(self, t, pre):
        self.t = t
        self.pre = pre
        self.n = len(self.t)
        self.G = list(self.t)
        self.fixed = [False] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if ((not self.fixed[j]) and all(self.fixed[i] for i in self.pre[j])):
                    self.G[j] = max(self.t[j], max(((self.G[i] + self.t[j]) for i in self.pre[j]), default=-2147483647))
                    self.fixed[j] = True
                    _changed = True
        return self.G


