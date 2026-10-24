"""LLP-LIS. The book states B as two conjuncts, G[j] >= 1 and
G[j] >= max{G[i]+1 | i in pre(j)}, and proves it lattice-linear; one
`ensure` over their maximum says exactly that, so the forbidden
predicate and the advance are read off B rather than restated.
pre(j) = {i < j : A[i] < A[j]}, so priority(j) = j settles each index
in one advance -- the schedule the book's `fixed` array records, an
efficiency choice rather than part of the predicate.

Auto-generated from LLPLIS.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPLIS:
    def LLPLIS(self, A, pre):
        self.A = A
        self.pre = pre
        self.n = len(self.A)
        self.G = [1] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if (not (self.G[j] >= max(1, max(((self.G[i] + 1) for i in self.pre[j]), default=-2147483647)))):
                    self.G[j] = max(1, max(((self.G[i] + 1) for i in self.pre[j]), default=-2147483647))
                    _changed = True
        return self.G


