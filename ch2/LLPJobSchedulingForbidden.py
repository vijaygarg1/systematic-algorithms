"""LLP job scheduling: forbidden when G[j] < max_{i in pre[j]} G[i] + t[j].

Auto-generated from LLPJobSchedulingForbidden.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPJobSchedulingForbidden:
    def LLPJobSchedulingForbidden(self, t, pre):
        self.t = t
        self.pre = pre
        self.n = len(self.t)
        self.G = list(self.t)
        # topological order over pre, declared acyclic
        _indeg = [0] * (self.n)
        _succ = [[] for _ in range(self.n)]
        for _k in range(0, (self.n - 1) + 1):
            for _i in self.pre[_k]:
                if _i >= 0 and _i < self.n:
                    _indeg[_k] += 1
                    _succ[_i].append(_k)
        _q = []
        for _k in range(0, (self.n - 1) + 1):
            if _indeg[_k] == 0:
                _q.append(_k)
        _seen = 0
        _h = 0
        j = 0
        while _h < len(_q):
            j = _q[_h]
            _h += 1
            _seen += 1
            if (self.G[j] < max(((self.G[i] + self.t[j]) for i in self.pre[j]), default=-2147483647)):
                self.G[j] = max(((self.G[i] + self.t[j]) for i in self.pre[j]), default=-2147483647)
            for _x in _succ[j]:
                _indeg[_x] -= 1
                if _indeg[_x] == 0:
                    _q.append(_x)
        _total = (self.n - 1) - (0) + 1
        if _seen != _total:
            raise ValueError('pre has a cycle; setAcyclic promises it does not')
        return self.G


