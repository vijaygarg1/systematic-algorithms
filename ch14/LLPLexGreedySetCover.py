"""LLP parallel H_n-approximation for Set Cover: pick every set that
maximises coverage among its neighbours and is lex-minimal among ties.
A neighbour of j is another unpicked set sharing an uncovered element
with j; j is forbidden (i.e. should be picked) when it still covers
something and no neighbour outranks it.

The predicate is tested without comparing set pairs. For each uncovered
element keep best[e], the unpicked set covering the most uncovered
elements that contains e (lowest index on ties); then j outranks every
neighbour exactly when best[e] = j for every uncovered e in s_j, since
the neighbours of j are precisely the unpicked sets sharing such an e.
The coverage counts cnt[j] = |s_j ∩ R| and the covered[] flags are
maintained by the advance, so coverage is read in O(1) instead of being
recomputed -- it used to cost an O(m) scan per element, inside an O(u)
scan per set, inside an O(m) scan over neighbours.

The instance is a pair of incidence lists: mem[s] holds the elements of
set s and inc[e] holds the sets containing element e. Neither is an
m x u matrix, so the whole instance is O(N) -- N being the number of
(set, element) incidences -- rather than Theta(m*u). Every pass below
walks one of these lists, so each costs O(N) and not O(m*u).

That matters most for best[], which is rebuilt after every advance, and
there are as many advances as sets picked: rebuilding it by scanning a
whole matrix each time made the algorithm O(m*u) per advance, a full
factor of n more than the chapter's O(N*min(m,n)) bound.

Auto-generated from LLPLexGreedySetCover.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPLexGreedySetCover:
    def LLPLexGreedySetCover(self, mem, inc):
        self.mem = mem
        self.inc = inc
        self.n = len(self.mem)
        self.m = len(self.mem)
        self.u = len(self.inc)
        self.G = [False] * (self.n)
        self.covered = [False] * (self.u)
        self.cnt = [0] * (self.m)
        self.best = [0] * (self.u)
        self.fresh = False
        for s in range(0, ((self.m - 1)) + 1):
            self.cnt[s] = len(self.mem[s])
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self.isLexMaxCov(j):
                    self.G[j] = True
                    k = 0
                    while (k < len(self.mem[j])):
                        e = self.mem[j][k]
                        if (not self.covered[e]):
                            self.covered[e] = True
                            for t in range(0, ((len(self.inc[e]) - 1)) + 1):
                                self.cnt[self.inc[e][t]] = (self.cnt[self.inc[e][t]] - 1)
                        k = (k + 1)
                    self.fresh = False
                    _changed = True
        return self.G

    def isLexMaxCov(self, j):
        if self.G[j]:
            return False
        self.refresh()
        if (self.cnt[j] <= 0):
            return False
        k = 0
        while (k < len(self.mem[j])):
            e = self.mem[j][k]
            if ((not self.covered[e]) and (self.best[e] != j)):
                return False
            k = (k + 1)
        return True

    def refresh(self):
        if (not self.fresh):
            for e in range(0, ((self.u - 1)) + 1):
                self.best[e] = self.bestAt(e)
            self.fresh = True

    def bestAt(self, e):
        win = (0 - 1)
        for k in range(0, ((len(self.inc[e]) - 1)) + 1):
            s = self.inc[e][k]
            if (not self.G[s]):
                if (win < 0):
                    win = s
                else:
                    if (self.cnt[s] > self.cnt[win]):
                        win = s
        return win


if __name__ == '__main__':
    mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = LLPLexGreedySetCover()
    result = prog.LLPLexGreedySetCover(mem, inc)
    print(result)

