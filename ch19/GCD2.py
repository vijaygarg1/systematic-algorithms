"""GCD2: ascending GCD via ceiling-ratio operations.
Equivalent forbidden / advance form of the ensure-clause version
G[j] >= max_i (A[j] * G[i] + A[i] - 1) / A[i].

Forbidden picks any i for which the bound is currently violated;
advance lifts G[j] to that single ratio.  Iterating over forbidden i's
converges to the maximum because the ensure constraint is monotone.

Auto-generated from GCD2.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class GCD2:
    def GCD2(self, A):
        self.A = A
        self.n = len(self.A)
        self.G = [0] * (self.n)
        for k in range(0, ((self.n - 1)) + 1):
            self.G[k] = 1
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self._pick_i(j):
                    self.G[j] = int((((self.A[j] * self.G[self.i]) + self.A[self.i]) - 1) / self.A[self.i])
                    _changed = True
        return self.G

    def _pick_i(self, j):
        for i in range(0, ((self.n - 1)) + 1):
            if ((self.G[j] * self.A[i]) < (self.A[j] * self.G[i])):
                self.i = i
                return True
        return False


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = GCD2()
    result = prog.GCD2(A)
    print(result)

