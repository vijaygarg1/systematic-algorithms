"""Minimizing maximum lateness, as bxx-greedy.tex's Algorithm
MinMaxLateness states it:

last := 0; lateness := 0
for i := 1 to n:
finish := last + t[i]
G[i] := last
last := finish
if finish > d[i]: lateness := max(lateness, finish - d[i])
return G, lateness

Jobs arrive sorted by deadline, which is the greedy choice that
minimizes the maximum lateness.

The loop is `for`, not `forall`: last and lateness both carry from one
iteration to the next.

The book returns the pair (G, lateness). LL returns one value, so G is
returned and lateness is computed alongside it, exactly as the book
computes it.

Auto-generated from MinMaxLateness.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MinMaxLateness:
    def MinMaxLateness(self, t, d):
        self.t = t
        self.d = d
        self.n = len(self.t)
        self.G = [0] * (self.n)
        self.last = 0
        self.lateness = 0
        for i in range(0, ((self.n - 1)) + 1):
            finish = (self.last + self.t[i])
            self.G[i] = self.last
            self.last = finish
            if (finish > self.d[i]):
                self.lateness = max(self.lateness, (finish - self.d[i]))
        return self.G


if __name__ == '__main__':
    t = [0, 0, 1, 2]
    d = [0, 0, 1, 1]
    prog = MinMaxLateness()
    result = prog.MinMaxLateness(t, d)
    print(result)

