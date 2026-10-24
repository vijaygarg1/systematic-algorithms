"""Ord: multiplicative order of a modulo n via descending LLP on divisor lattice.
G[i] = exponent of p[i] in the current candidate k = prod p[i]^G[i].

Auto-generated from Ord.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Ord:
    def Ord(self, a, n, p, e):
        self.a = a
        self.n = n
        self.p = p
        self.e = e
        self.s = len(self.p)
        self.G = list(self.e)
        k = 1
        i = 0
        while (i < self.s):
            j = 0
            while (j < self.e[i]):
                k = (k * self.p[i])
                j = (j + 1)
            i = (i + 1)
        changed = True
        while changed:
            changed = False
            i = 0
            while (i < self.s):
                if ((self.G[i] > 0) and (self.modpow(self.a, int(k / self.p[i]), self.n) == 1)):
                    self.G[i] = (self.G[i] - 1)
                    k = int(k / self.p[i])
                    changed = True
                i = (i + 1)
        return k

    def modpow(self, base, exp, mod):
        result = 1
        b = (base % mod)
        e = exp
        while (e > 0):
            if ((e % 2) == 1):
                result = ((result * b) % mod)
            e = int(e / 2)
            b = ((b * b) % mod)
        return result


if __name__ == '__main__':
    a = 0
    p = [2, 3, 5, 7]
    e = [0, 0, 1, 2]
    prog = Ord()
    result = prog.Ord(a, len(p), p, e)
    print(result)

