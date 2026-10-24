"""Mandatory: composition program forcing the intervals in subset S
into the schedule.  Halts with "infeasible" (returns null) when two
mandatory intervals overlap.  Composed onto LLP-IntervalScheduling
via predicate conjunction:
[ LLP-IntervalScheduling(s, f, G) && Mandatory(S, G) ].

Auto-generated from MandatoryIntervals.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MandatoryIntervals:
    def MandatoryIntervals(self, s, f, S):
        self.s = s
        self.f = f
        self.S = S
        self.n = len(self.s)
        self.G = [False] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (self.S[j] and (not self.G[j])):
                    if any((self.G[k] and self.overlap(j, k, self.s, self.f)) for k in range(0, ((self.n - 1)) + 1)):
                        return None
                    self.G[j] = True
                    _changed = True
        return self.G

    def overlap(self, j, k, s, f):
        return ((s[j] < f[k]) and (s[k] < f[j]))


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    S = [False, False, False, False]
    prog = MandatoryIntervals()
    result = prog.MandatoryIntervals(s, f, S)
    print(result)

