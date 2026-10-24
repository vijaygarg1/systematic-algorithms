"""Classical FPTAS for 0/1 Knapsack: scale the profits down by
mu = eps*L/nf, then solve the scaled problem exactly by a profit-indexed
DP. Rounding loses at most eps*OPT, so the whole thing is polynomial in
nf and 1/eps.

L is a 2-approximation lower bound on OPT -- the better of a density-greedy
solution and the most profitable single item -- rather than the maximum
profit M. Two consequences: scaling by L still loses only nf*mu = eps*L
<= eps*OPT, and since OPT <= 2L no feasible solution can have scaled
profit above 2*nf/eps, so the profit axis is capped there. That axis is
O(nf/eps) wide instead of the O(nf^2/eps) that scaling by M forces, which
is what takes the table (and the running time) from O(nf^3/eps) to
O(nf^2/eps).

D[i][p] = least total weight of a subset of the first i feasible items
whose scaled profit is at least p, with D[i][0] = 0 and every other
cell starting at INF. The answer is the largest p with D[nf][p] <= W;
the traceback then walks the rows back, taking item i exactly when
including it is what made row i cheaper than row i-1.

Auto-generated from FPTASKnapsack.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class FPTASKnapsack:
    def FPTASKnapsack(self, w, v, W, epsNum, epsDen):
        self.w = w
        self.v = v
        self.W = W
        self.epsNum = epsNum
        self.epsDen = epsDen
        self.n = len(self.w)
        self.S = [False] * (len(self.w))
        self.orig = [0] * (len(self.w))
        self.nf = 0
        for t in range(0, ((len(self.w) - 1)) + 1):
            if (self.w[t] <= self.W):
                self.orig[self.nf] = t
                self.nf = (self.nf + 1)
        if (self.nf == 0):
            return self.S
        fw = [0] * (self.nf)
        fv = [0] * (self.nf)
        k = 0
        while (k < self.nf):
            fw[k] = self.w[self.orig[k]]
            fv[k] = self.v[self.orig[k]]
            k = (k + 1)
        M = max((fv[j] for j in range(0, ((self.nf - 1)) + 1)), default=-2147483647)
        used = [False] * (self.nf)
        room = self.W
        gval = 0
        picks = 0
        while (picks < self.nf):
            cand = (0 - 1)
            t = 0
            while (t < self.nf):
                if ((not used[t]) and (fw[t] <= room)):
                    if (cand < 0):
                        cand = t
                    else:
                        if ((fv[t] * fw[cand]) > (fv[cand] * fw[t])):
                            cand = t
                t = (t + 1)
            if (cand < 0):
                picks = self.nf
            else:
                used[cand] = True
                gval = (gval + fv[cand])
                room = (room - fw[cand])
                picks = (picks + 1)
        L = gval
        if (M > L):
            L = M
        vPrime = [0] * (self.nf)
        total = 0
        INF = 1
        k = 0
        while (k < self.nf):
            vPrime[k] = int(((fv[k] * self.nf) * self.epsDen) / (self.epsNum * L))
            total = (total + vPrime[k])
            INF = (INF + fw[k])
            k = (k + 1)
        Vp = (int(((2 * self.nf) * self.epsDen) / self.epsNum) + 1)
        if (total < Vp):
            Vp = total
        D = [[0] * ((Vp + 1)) for _ in range((self.nf + 1))]
        for a in range(0, (self.nf) + 1):
            for b in range(1, (Vp) + 1):
                D[a][b] = INF
        for i in range(1, (self.nf) + 1):
            for p in range(1, (Vp) + 1):
                D[i][p] = min(D[(i - 1)][p], (fw[(i - 1)] + D[(i - 1)][max(0, (p - vPrime[(i - 1)]))]))
        bp = 0
        for q in range(0, (Vp) + 1):
            if (D[self.nf][q] <= self.W):
                bp = q
        i = self.nf
        while (i > 0):
            if (D[i][bp] < D[(i - 1)][bp]):
                self.S[self.orig[(i - 1)]] = True
                bp = max(0, (bp - vPrime[(i - 1)]))
            i = (i - 1)
        return self.S


if __name__ == '__main__':
    w = [0, 0, 1, 2]
    v = [0, 0, 1, 1]
    W = 10
    epsNum = 1
    epsDen = 2
    prog = FPTASKnapsack()
    result = prog.FPTASKnapsack(w, v, W, epsNum, epsDen)
    print(result)

