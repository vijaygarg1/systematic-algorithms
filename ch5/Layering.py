"""Layering, as bxx-graph.tex's Algorithm Layering states it:

var Q: queue of int                       // initially empty
indeg[j] := |pre(j)| for all j
forall j in V: if indeg[j] = 0: G[j] := 0; Q.enqueue(j)
while not Q.empty():
j := Q.dequeue()
forall k in succ(j):
indeg[k] := indeg[k] - 1
if indeg[k] = 0: G[k] := 1 + max_{i in pre(k)} G[i]; Q.enqueue(k)
return G

Kahn's algorithm: a vertex gets its layer once every predecessor has
one. The book's `queue of int` is a list<int> -- add is enqueue,
removeFirst is dequeue.

Auto-generated from Layering.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Layering:
    def Layering(self, pre, succ):
        self.pre = pre
        self.succ = succ
        self.n = len(self.pre)
        self.G = [0] * (self.n)
        self.indeg = [0] * (self.n)
        for j in range(0, ((self.n - 1)) + 1):
            self.indeg[j] = len(self.pre[j])
        Q = []
        for j in range(0, ((self.n - 1)) + 1):
            if (self.indeg[j] == 0):
                self.G[j] = 0
                Q.append(j)
        while (not (not Q)):
            j = Q.pop(0)
            for k in self.succ[j]:
                self.indeg[k] = (self.indeg[k] - 1)
                if (self.indeg[k] == 0):
                    self.G[k] = (1 + max((self.G[i] for i in self.pre[k]), default=-2147483647))
                    Q.append(k)
        return self.G


