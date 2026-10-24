"""LLP FPTAS for Knapsack, matching bxx-approximate.tex Algorithm
LLP-ApproxKnapsack (fig:llp-approxknapsack): discard items that cannot
fit, then run a profit-indexed min-weight DP D[i][p] on a lattice of
size (nf+1) x (V'+1), V' = O(nf/eps), where nf is the number of
feasible items. Fully polynomial.

The profits are scaled by mu = eps*L/nf against a 2-approximation lower
bound L on OPT (the better of a density-greedy solution and the most
profitable single item), not against the maximum profit M. Scaling by L
still loses only nf*mu = eps*L <= eps*OPT, and because OPT <= 2L no
feasible solution reaches scaled profit beyond 2*nf/eps, so the axis is
capped there: O(nf/eps) columns instead of O(nf^2/eps), taking the lattice
from O(nf^3/eps) to O(nf^2/eps).
D[i][p] = minimum total weight of a subset of the first i feasible
items with scaled profit >= p; the answer is max { p : D[nf][p] <= W },
profit mu * p.

Every value the guard or its advance touches by bare name (D, fw,
vPrime, nf, Vp) must be a class field for the auto-generated
forbidden/advance helpers to see it, and the compiler grants that only
to a *leading, uninterrupted run* of declarations at the top of the
entry method. Each preprocessing step is therefore written as a single
declaration initialised by a helper call, so the run is never broken;
the loops themselves live in those helpers below.

The guard's shared bound n is set to max(nf,Vp)+1 (rather than nf+1 or
Vp+1 individually, since the compiler infers one bound for every index
of a guard) and the predicate explicitly re-restricts i to [1..nf] and
p to [1..Vp], so out-of-range (i,p) pairs in the padded square scan are
simply never forbidden.

The aggregates deliberately avoid i and p as bound variables: a
quantifier helper is generated taking the guard's index variables as
parameters, so a bound i inside a forbidden (i, p) program collides
with its own parameter and will not compile.

Auto-generated from LLPApproxKnapsack.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPApproxKnapsack:
    def LLPApproxKnapsack(self, w, v, W, epsNum, epsDen):
        self.w = w
        self.v = v
        self.W = W
        self.epsNum = epsNum
        self.epsDen = epsDen
        self.n = len(self.w)
        self.feas = self.feasible(self.w, self.W)
        self.nf = len(self.feas)
        self.fw = self.gather(self.w, self.feas)
        self.fv = self.gather(self.v, self.feas)
        self.M = max((self.fv[k] for k in range(0, ((self.nf - 1)) + 1)), default=-2147483647)
        self.L = self.lowerBound(self.fw, self.fv, self.nf, self.W, self.M)
        self.vPrime = self.scaled(self.fv, self.nf, self.epsNum, self.epsDen, self.L)
        self.Vp = self.cappedAxis(self.vPrime, self.nf, self.epsNum, self.epsDen)
        self.INF = (1 + sum(self.fw[k] for k in range(0, ((self.nf - 1)) + 1)))
        self.D = [[0] * ((self.Vp + 1)) for _ in range((self.nf + 1))]
        for a in range(0, (self.nf) + 1):
            for b in range(1, (self.Vp) + 1):
                self.D[a][b] = self.INF
        self.n = (max(self.nf, self.Vp) + 1)
        _changed = True
        while _changed:
            _changed = False
            for i in range(0, ((self.n - 1)) + 1):
                for p in range(0, ((self.n - 1)) + 1):
                    if (((((i >= 1) and (i <= self.nf)) and (p >= 1)) and (p <= self.Vp)) and (self.D[i][p] > min(self.D[(i - 1)][p], (self.fw[(i - 1)] + self.D[(i - 1)][max(0, (p - self.vPrime[(i - 1)]))])))):
                        self.D[i][p] = min(self.D[(i - 1)][p], (self.fw[(i - 1)] + self.D[(i - 1)][max(0, (p - self.vPrime[(i - 1)]))]))
                        _changed = True
        return self.bestProfit()

    def bestProfit(self):
        best = 0
        for q in range(0, (self.Vp) + 1):
            if (self.D[self.nf][q] <= self.W):
                best = q
        return best

    def feasible(self, w, W):
        idx = [0] * (len(w))
        c = 0
        for t in range(0, ((len(w) - 1)) + 1):
            if (w[t] <= W):
                idx[c] = t
                c = (c + 1)
        return idx[0:((c - 1)) + 1]

    def gather(self, a, idx):
        out = [0] * (len(idx))
        for k in range(0, ((len(idx) - 1)) + 1):
            out[k] = a[idx[k]]
        return out

    def scaled(self, fv, nf, epsNum, epsDen, L):
        out = [0] * (nf)
        for k in range(0, ((nf - 1)) + 1):
            out[k] = int(((fv[k] * nf) * epsDen) / (epsNum * L))
        return out

    def lowerBound(self, fw, fv, nf, W, M):
        used = [False] * (nf)
        room = W
        gval = 0
        picks = 0
        while (picks < nf):
            cand = (0 - 1)
            t = 0
            while (t < nf):
                if ((not used[t]) and (fw[t] <= room)):
                    if (cand < 0):
                        cand = t
                    else:
                        if ((fv[t] * fw[cand]) > (fv[cand] * fw[t])):
                            cand = t
                t = (t + 1)
            if (cand < 0):
                picks = nf
            else:
                used[cand] = True
                gval = (gval + fv[cand])
                room = (room - fw[cand])
                picks = (picks + 1)
        if (M > gval):
            return M
        return gval

    def cappedAxis(self, vPrime, nf, epsNum, epsDen):
        total = sum(vPrime[k] for k in range(0, ((nf - 1)) + 1))
        capv = (int(((2 * nf) * epsDen) / epsNum) + 1)
        if (total < capv):
            return total
        return capv


if __name__ == '__main__':
    w = [0, 0, 1, 2]
    v = [0, 0, 1, 1]
    W = 10
    epsNum = 1
    epsDen = 2
    prog = LLPApproxKnapsack()
    result = prog.LLPApproxKnapsack(w, v, W, epsNum, epsDen)
    print(result)

