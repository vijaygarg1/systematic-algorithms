"""Par-QuadCRT: simultaneous quadratic congruences x^2 ≡ a[j] (mod m[j]).
Generalises Par-CRT to a non-linear local predicate.  G[j] is initialised
to the smallest non-negative root of x^2 ≡ a[j] (mod m[j]); the
forbidden / advance pair drives every G[j] up to a common value that
satisfies all r congruences simultaneously.

Auto-generated from ParQuadCRT.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ParQuadCRT:
    def ParQuadCRT(self, m, a, roots):
        self.m = m
        self.a = a
        self.roots = roots
        self.n = len(self.m)
        self.G = list(self.roots)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self._pick_i(j):
                    self.G[j] = (self.G[j] + (int((((self.G[self.i] - self.G[j]) + self.m[j]) - 1) / self.m[j]) * self.m[j]))
                    _changed = True
        return self.G

    def _pick_i(self, j):
        for i in range(0, ((self.n - 1)) + 1):
            if (self.G[j] < self.G[i]):
                self.i = i
                return True
        return False


if __name__ == '__main__':
    m = [3, 5, 7, 11]
    a = [0, 0, 1, 2]
    roots = [0, 0, 1, 1]
    prog = ParQuadCRT()
    result = prog.ParQuadCRT(m, a, roots)
    print(result)

