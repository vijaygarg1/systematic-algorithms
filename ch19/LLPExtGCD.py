"""Extended Euclidean algorithm, cast as an LLP forbidden/advance program
(matching the book's LLP-ExtGCD algorithm box in bxx-numberTheory.tex,
\S Extended GCD Algorithm): maintains G alongside Bezout coefficients
(X, Y) with the invariant G[k] = X[k]*a + Y[k]*b. Forbidden picks the
larger of the two entries; advance reduces it via the same quotient
q := floor((G[j]-1)/G[i]) used by LLP-GCD, applying the identical
subtraction to the Bezout pair so the invariant is preserved. At
termination G[0] = G[1] = gcd(a,b) and (X[0],Y[0]) is a Bezout pair.

All three state arrays (G, X, Y) must be declared consecutively, before
any assignment statement -- the parser only promotes a *leading,
uninterrupted* run of `TYPE NAME = ...;` declarations to instance
fields visible from forbidden/advance; an assignment statement between
two declarations ends that run early, silently leaving the later ones
as method-local variables invisible to advance() (a real llc.py
limitation, confirmed and documented rather than worked around in the
compiler, since only this one program has needed more than one top-
level state array so far). `n` likewise has to be set explicitly here:
it is normally inferred from an array *parameter*'s length, but G/X/Y
are locally allocated, not parameters, so nothing would ever set it
otherwise -- left at Java's default 0, the main loop would silently
iterate zero times.

Auto-generated from LLPExtGCD.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPExtGCD:
    def LLPExtGCD(self, a, b):
        self.a = a
        self.b = b
        self.G = [0] * (2)
        self.X = [0] * (2)
        self.Y = [0] * (2)
        self.n = 2
        self.G[0] = self.a
        self.G[1] = self.b
        self.X[0] = 1
        self.Y[0] = 0
        self.X[1] = 0
        self.Y[1] = 1
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if self._pick_i(j):
                    q = int((self.G[j] - 1) / self.G[self.i])
                    self.G[j] = (self.G[j] - (q * self.G[self.i]))
                    self.X[j] = (self.X[j] - (q * self.X[self.i]))
                    self.Y[j] = (self.Y[j] - (q * self.Y[self.i]))
                    _changed = True
        result = [0] * (3)
        result[0] = self.G[0]
        result[1] = self.X[0]
        result[2] = self.Y[0]
        return result

    def _pick_i(self, j):
        for i in range(0, (1) + 1):
            if (self.G[j] > self.G[i]):
                self.i = i
                return True
        return False


if __name__ == '__main__':
    a = 0
    b = 0
    prog = LLPExtGCD()
    result = prog.LLPExtGCD(a, b)
    print(result)

