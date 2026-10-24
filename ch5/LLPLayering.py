"""LLP-Layering: G[j] is the length of the longest path from a source to j.
The forbidden predicate is the negation of B_layer restricted to j's
incoming edges, so no index is forbidden exactly when B_layer holds.
A vertex with no predecessors is never forbidden and keeps its initial
label 0.

Layering is defined only on a directed ACYCLIC graph -- a cycle has no
longest path -- so `setAcyclic(pre)` states that as a condition on valid
input.  It is the precondition that makes the schedule available: on an
acyclic graph every index can be settled once, in topological order, so
that when j is advanced all of its predecessors already hold their final
values.  That is the schedule the boolean `fixed` array implements in the
hand-written ports, and it is what gives the O(n+m) of Section 5.5.
Without the promise the program can only relax to a fixpoint, revisiting
a vertex every time a predecessor deepens, which costs O(n*m).

Auto-generated from LLPLayering.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPLayering:
    def LLPLayering(self, pre):
        self.pre = pre
        self.n = len(self.pre)
        self.G = [0] * (self.n)
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
            if any((self.G[j] <= self.G[i]) for i in self.pre[j]):
                self.G[j] = max(0, max(((self.G[i] + 1) for i in self.pre[j]), default=-2147483647))
            for _x in _succ[j]:
                _indeg[_x] -= 1
                if _indeg[_x] == 0:
                    _q.append(_x)
        _total = (self.n - 1) - (0) + 1
        if _seen != _total:
            raise ValueError('pre has a cycle; setAcyclic promises it does not')
        return self.G


