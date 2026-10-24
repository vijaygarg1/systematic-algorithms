"""Kahn's-style topological sweep: finalise each job once its predecessors are fixed.

Auto-generated from LLPJobSchedulingWithFixed.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPJobSchedulingWithFixed:
    def LLPJobSchedulingWithFixed(self, t, pre, succ):
        self.t = t
        self.pre = pre
        self.succ = succ
        self.n = len(self.t)
        n = len(self.t)
        G = [0] * (n)
        count = [0] * (n)
        candidate = [0] * (n)
        head = 0
        tail = 0
        for k in range(0, ((n - 1)) + 1):
            G[k] = self.t[k]
        for k in range(0, ((n - 1)) + 1):
            count[k] = len(self.pre[k])
        for k in range(0, ((n - 1)) + 1):
            if (count[k] == 0):
                candidate[tail] = k
                tail = (tail + 1)
        while (head < tail):
            j = candidate[head]
            head = (head + 1)
            G[j] = max(G[j], max(((G[i] + self.t[j]) for i in self.pre[j]), default=-2147483647))
            for k in self.succ[j]:
                count[k] = (count[k] - 1)
                if (count[k] == 0):
                    candidate[tail] = k
                    tail = (tail + 1)
        return G


