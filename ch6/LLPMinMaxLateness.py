"""LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start
time G[j] to the prefix sum of earlier processing times.

The targets are the prefix sums S[j] = t[0] + ... + t[j-1], and they are
built once in a left-to-right scan rather than written inline as
`sum i in [0..j-1] : t[i]`.  Written inline, that reduction is an O(j)
loop the guard re-runs for every j -- and the advance runs it a second
time -- so the program costs O(n^2) even though every target is already
known from the one before it.  Computing them in a single scan is what
makes the stated O(n) bound (Section 6.3, "a left-to-right scan") true
of this program and not just of the algorithm it encodes.

The deadlines d are the sort order of the input, not something the
predicate reads: j's target depends only on the processing times ahead
of it in that order.

Auto-generated from LLPMinMaxLateness.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPMinMaxLateness:
    def LLPMinMaxLateness(self, t, d):
        self.t = t
        self.d = d
        self.n = len(self.t)
        self.S = [0] * (self.n)
        self.G = [0] * (self.n)
        for j in range(1, ((self.n - 1)) + 1):
            self.S[j] = (self.S[(j - 1)] + self.t[(j - 1)])
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (not (self.G[j] >= self.S[j])):
                    self.G[j] = self.S[j]
                    _changed = True
        lateness = max((max(0, ((self.G[j] + self.t[j]) - self.d[j])) for j in range(0, ((self.n - 1)) + 1)), default=-2147483647)
        return self.G


if __name__ == '__main__':
    t = [0, 0, 1, 2]
    d = [0, 0, 1, 1]
    prog = LLPMinMaxLateness()
    result = prog.LLPMinMaxLateness(t, d)
    print(result)

