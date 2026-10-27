"""Classical sequential weighted-interval-scheduling DP. Intervals are
presorted once by finish time, with index 0 a 0-weight sentinel, so
p[j] -- the last interval that finishes no later than j starts -- is
derived here rather than supplied: it is the largest such index, or the
sentinel 0 when no earlier interval fits.

Auto-generated from WeightedIntervalScheduling.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class WeightedIntervalScheduling:
    def schedule(self, s, f, w):
        self.s = s
        self.f = f
        self.w = w
        self.n = len(self.s)
        self.p = [0] * (self.n)
        self.G = [0] * (self.n)
        self.sel = [0] * (self.n)
        for j in range(1, ((self.n - 1)) + 1):
            self.p[j] = self.lastCompatible(j, self.s, self.f)
        self.G[0] = 0
        for cur in range(1, ((self.n - 1)) + 1):
            self.G[cur] = max((self.w[cur] + self.G[self.p[cur]]), self.G[(cur - 1)])
        cur = (self.n - 1)
        while (cur > 0):
            if ((self.w[cur] + self.G[self.p[cur]]) >= self.G[(cur - 1)]):
                self.sel[cur] = 1
                cur = self.p[cur]
            else:
                cur = (cur - 1)
        return self.sel

    def lastCompatible(self, j, s, f):
        lo = 1
        hi = (j - 1)
        best = 0
        while (lo <= hi):
            mid = int((lo + hi) / 2)
            if (f[mid] <= s[j]):
                best = mid
                lo = (mid + 1)
            else:
                hi = (mid - 1)
        return best


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    w = [0, 0, 0, 2]
    prog = WeightedIntervalScheduling()
    result = prog.schedule(s, f, w)
    print(result)

