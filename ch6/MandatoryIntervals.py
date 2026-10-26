"""Mandatory-Intervals: interval scheduling when subset S must appear in the answer.
Returns null ("infeasible") exactly when two mandatory intervals overlap.
Intervals arrive indexed in finish-time order.
NOT a conjunct on LLP-IntervalScheduling: that composition rejects feasible
instances (interval 0=[0,2) optional, 1=[1,3) mandatory). Three phases instead.

Auto-generated from MandatoryIntervals.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MandatoryIntervals:
    def MandatoryIntervals(self, s, f, S):
        self.s = s
        self.f = f
        self.S = S
        self.n = len(self.s)
        self.sel = [False] * (self.n)
        for j in range(0, ((self.n - 1)) + 1):
            if self.S[j]:
                for k in range(0, ((self.n - 1)) + 1):
                    if ((self.S[k] and (k != j)) and self.overlap(j, k, self.s, self.f)):
                        return None
                self.sel[j] = True
        cand = [False] * (self.n)
        for j in range(0, ((self.n - 1)) + 1):
            if (not self.S[j]):
                ok = True
                for k in range(0, ((self.n - 1)) + 1):
                    if (self.S[k] and self.overlap(j, k, self.s, self.f)):
                        ok = False
                cand[j] = ok
        chosen = False
        lastFinish = 0
        for j in range(0, ((self.n - 1)) + 1):
            if cand[j]:
                if ((not chosen) or (self.s[j] >= lastFinish)):
                    self.sel[j] = True
                    lastFinish = self.f[j]
                    chosen = True
        return self.sel

    def overlap(self, j, k, s, f):
        return ((s[j] < f[k]) and (s[k] < f[j]))


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    S = [False, False, False, False]
    prog = MandatoryIntervals()
    result = prog.MandatoryIntervals(s, f, S)
    print(result)

