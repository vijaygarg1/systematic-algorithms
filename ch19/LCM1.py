"""LCM1: ascending LLP to find least common multiple.
Forbidden when G[j] < G[i]; advance jumps to next multiple of A[j] >= G[i].

Auto-generated from LCM1.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LCM1:
    def LCM1(self, A):
        self.A = A
        self.n = len(self.A)
        self.G = list(self.A)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self._pick_i(j):
                    self.G[j] = (self.G[j] + (int((((self.G[self.i] - self.G[j]) + self.A[j]) - 1) / self.A[j]) * self.A[j]))
                    _changed = True
        return self.G

    def _pick_i(self, j):
        for i in range(0, ((self.n - 1)) + 1):
            if (self.G[j] < self.G[i]):
                self.i = i
                return True
        return False


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LCM1()
    result = prog.LCM1(A)
    print(result)

