"""Par-CRT: ascending parallel Chinese Remainder Theorem.
Forbidden when G[j] < G[i] for some i;
advance jumps to next multiple-of-m[j] congruence >= G[i].

Auto-generated from ParCRT.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ParCRT:
    def ParCRT(self, m, b):
        self.m = m
        self.b = b
        self.n = len(self.m)
        self.G = list(self.b)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
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
    b = [0, 0, 1, 2]
    prog = ParCRT()
    result = prog.ParCRT(m, b)
    print(result)

