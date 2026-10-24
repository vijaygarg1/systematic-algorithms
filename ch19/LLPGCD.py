"""LLP-GCD: greatest common divisor of two numbers via Euclidean
reduction, matching bxx-numberTheory.tex Algorithm LLP-GCD. G is the
two-element vector (a, b); forbidden picks any entry strictly larger
than another, and advance replaces it by its remainder mod the smaller
(or by the smaller itself when it divides exactly, so entries stay at
least 1). At termination G[0] = G[1] = gcd(a, b).

Each advance replaces G[j] by G[j] mod G[i] < G[i] <= G[j], which at
least halves it, so the algorithm performs O(log(min(a,b))) advances --
the bound the chapter states. `n` has to be set explicitly, as in
LLPExtGCD: it is normally inferred from an array *parameter*'s length,
but G is locally allocated rather than passed in.

Auto-generated from LLPGCD.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPGCD:
    def LLPGCD(self, a, b):
        self.a = a
        self.b = b
        self.G = [0] * (2)
        self.n = 2
        self.G[0] = self.a
        self.G[1] = self.b
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
        return self.G[0]

    def _pick_i(self, j):
        for i in range(0, (1) + 1):
            if (self.G[j] > self.G[i]):
                self.i = i
                return True
        return False


if __name__ == '__main__':
    a = 0
    b = 0
    prog = LLPGCD()
    result = prog.LLPGCD(a, b)
    print(result)

