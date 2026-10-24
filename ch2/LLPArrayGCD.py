"""LLP-ArrayGCD: forbidden when some G[i] < G[j]; advance shrinks G[j] via mod.

Auto-generated from LLPArrayGCD.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPArrayGCD:
    def LLPArrayGCD(self, A):
        self.A = A
        self.n = len(self.A)
        self.G = list(self.A)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if self._pick_i(j):
                    if ((self.G[j] % self.G[self.i]) == 0):
                        self.G[j] = self.G[self.i]
                    else:
                        self.G[j] = (self.G[j] % self.G[self.i])
                    _changed = True
        return self.G

    def _pick_i(self, j):
        for i in range(0, ((self.n - 1)) + 1):
            if (self.G[j] > self.G[i]):
                self.i = i
                return True
        return False


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LLPArrayGCD()
    result = prog.LLPArrayGCD(A)
    print(result)

