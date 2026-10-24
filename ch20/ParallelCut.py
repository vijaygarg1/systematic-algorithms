"""ParallelCut: find the first consistent cut satisfying a conjunctive predicate.
Five-step parallel algorithm using state rejection and transitive closure.

Auto-generated from ParallelCut.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ParallelCut:
    def ParallelCut(self, vc, n, m):
        self.vc = vc
        self.n = n
        self.m = m
        self.F = [0] * (self.n)
        self.i = 0
        while (self.i < self.n):
            j = 0
            while (j < self.n):
                if ((self.i != j) and self.hb(self.vc, self.i, 0, j, 0, self.n)):
                    self.F[self.i] = 1
                j = (j + 1)
            self.i = (self.i + 1)
        sz = (self.n * self.m)
        R = [[False] * (sz) for _ in range(sz)]
        idx = 0
        while (idx < sz):
            R[idx][idx] = True
            idx = (idx + 1)
        self.i = 0
        while (self.i < self.n):
            j = 0
            while (j < (self.m - 1)):
                ip = 0
                while (ip < self.n):
                    if (ip != self.i):
                        jp = 0
                        while (jp < self.m):
                            if self.hb(self.vc, ip, jp, self.i, (j + 1), self.n):
                                R[((self.i * self.m) + j)][((ip * self.m) + jp)] = True
                            jp = (jp + 1)
                    ip = (ip + 1)
                j = (j + 1)
            self.i = (self.i + 1)
        self.transitiveClosure(R, sz)
        valid = [[False] * (self.m) for _ in range(self.n)]
        self.i = 0
        while (self.i < self.n):
            j = 0
            while (j < self.m):
                valid[self.i][j] = True
                j = (j + 1)
            self.i = (self.i + 1)
        self.i = 0
        while (self.i < self.n):
            if (self.F[self.i] == 1):
                ip = 0
                while (ip < self.n):
                    jp = 0
                    while (jp < self.m):
                        if R[((self.i * self.m) + 0)][((ip * self.m) + jp)]:
                            valid[ip][jp] = False
                        jp = (jp + 1)
                    ip = (ip + 1)
            self.i = (self.i + 1)
        cut = [0] * (self.n)
        self.i = 0
        while (self.i < self.n):
            cut[self.i] = (0 - 1)
            j = 0
            while (j < self.m):
                if valid[self.i][j]:
                    if ((j == 0) or (not valid[self.i][(j - 1)])):
                        cut[self.i] = j
                        j = self.m
                j = (j + 1)
            self.i = (self.i + 1)
        return cut

    def hb(self, vc, pi, si, pj, sj, n):
        idx1 = ((pi * n) + si)
        idx2 = ((pj * n) + sj)
        return ((vc[idx1][pj] >= sj) and (not ((pi == pj) and (si == sj))))

    def transitiveClosure(self, R, sz):
        k = 0
        while (k < sz):
            i = 0
            while (i < sz):
                j = 0
                while (j < sz):
                    if (R[i][k] and R[k][j]):
                        R[i][j] = True
                    j = (j + 1)
                i = (i + 1)
            k = (k + 1)


if __name__ == '__main__':
    vc = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    n = 0
    m = 0
    prog = ParallelCut()
    result = prog.ParallelCut(vc, n, m)
    print(result)

