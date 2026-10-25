"""Interval scheduling, as bxx-greedy.tex's Algorithm Interval states it:

G[1] := 1                       // first interval always selected
last := 1                       // index of last selected interval
for i := 2 to n:
if s[i] >= f[last]:
G[i] := 1                   // interval i is compatible
last := i

Intervals arrive sorted by finish time, so the earliest-finishing
compatible interval is always the right greedy choice.

The loop is `for`, not `forall`: last carries from one iteration to the
next, so the iterations are not independent.

The book indexes from 1 and selects G[1] unconditionally, so it assumes
n >= 1; this translation keeps that precondition.

Auto-generated from Interval.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Interval:
    def Interval(self, s, f):
        self.s = s
        self.f = f
        self.n = len(self.s)
        self.G = [0] * (self.n)
        self.G[0] = 1
        last = 0
        for i in range(1, ((self.n - 1)) + 1):
            if (self.s[i] >= self.f[last]):
                self.G[i] = 1
                last = i
        return self.G


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    prog = Interval()
    result = prog.Interval(s, f)
    print(result)

