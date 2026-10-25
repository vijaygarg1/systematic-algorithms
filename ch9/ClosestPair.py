"""Classical divide-and-conquer closest pair of points.

Px and Py hold the coordinates, indexed by point number, with the points
numbered in increasing x order. Ys lists this subproblem's point numbers
in increasing y order, which is what lets the strip scan stop after a
constant number of neighbours. G is the running answer: G[0] the best
SQUARED distance (squared, so no sqrt is ever needed), G[1] and G[2] the
two points achieving it.

The recursive worker is named ClosestPairOn rather than overloading
ClosestPair on arity. Java tolerates the overload, but Python does not:
the second `def ClosestPair` simply replaces the first, so the generated
ClosestPair.py called the 6-argument body with 5 arguments and died with
"missing 1 required positional argument: 'G'". The entry point keeps the
book's algorithm name; ClosestPairOn is the same function over an
explicit index range lo..hi with the running answer G threaded through.

Auto-generated from ClosestPair.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ClosestPair:
    def ClosestPair(self, lo, hi, Px, Py, Ys):
        self.lo = lo
        self.hi = hi
        self.Px = Px
        self.Py = Py
        self.Ys = Ys
        self.n = len(self.Px)
        self.G = [0.0] * (3)
        self.G[0] = float('inf')
        self.G[1] = (0 - 1)
        self.G[2] = (0 - 1)
        self.ClosestPairOn(self.lo, self.hi, self.Px, self.Py, self.Ys, self.G)
        return self.G

    def ClosestPairOn(self, lo, hi, Px, Py, Ys, G):
        if (hi <= lo):
            return
        if ((hi - lo) <= 2):
            for i in range(lo, ((hi - 1)) + 1):
                j = (i + 1)
                while (j <= hi):
                    self.check(i, j, Px, Py, G)
                    j = (j + 1)
            return
        mid = int((lo + hi) / 2)
        midX = Px[mid]
        n = ((hi - lo) + 1)
        Ls = [0] * (n)
        Rs = [0] * (n)
        nl = 0
        nr = 0
        k = 0
        while (k < n):
            p = Ys[k]
            if (p <= mid):
                Ls[nl] = p
                nl = (nl + 1)
            else:
                Rs[nr] = p
                nr = (nr + 1)
            k = (k + 1)
        self.ClosestPairOn(lo, mid, Px, Py, Ls, G)
        self.ClosestPairOn((mid + 1), hi, Px, Py, Rs, G)
        strip = [0] * (n)
        m = 0
        k = 0
        while (k < n):
            p = Ys[k]
            dx = (Px[p] - midX)
            if ((dx * dx) < G[0]):
                strip[m] = p
                m = (m + 1)
            k = (k + 1)
        for a in range(0, ((m - 1)) + 1):
            b = (a + 1)
            limit = (a + 15)
            while ((b < m) and (b <= limit)):
                self.check(strip[a], strip[b], Px, Py, G)
                b = (b + 1)

    def check(self, i, j, Px, Py, G):
        dx = (Px[i] - Px[j])
        dy = (Py[i] - Py[j])
        d = ((dx * dx) + (dy * dy))
        if (d < G[0]):
            G[0] = d
            G[1] = i
            G[2] = j


if __name__ == '__main__':
    Px = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    Py = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    Ys = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = ClosestPair()
    result = prog.ClosestPair(0, len(Ys) - 1, Px, Py, Ys)
    print(result)

