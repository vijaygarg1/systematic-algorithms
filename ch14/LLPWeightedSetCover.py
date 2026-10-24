"""LLP-WeightedSetCover: primal-dual f-approximation for weighted set
cover, matching bxx-approximate.tex Algorithm LLP-WeightedSetCover
(fig:llp-wsc). G[x] is the price on element x; a set s is tight when
its incident prices saturate w(s); an element is slack when it lies
in no tight set; slackMin(x) further requires x to be lex-minimal
among every slack element sharing a set with it. Advancing raises
x's price by the smallest remaining budget among the sets containing
it, tightening at least one of them.

The guard names its own range, x in [0..u-1], because x ranges over
ELEMENTS while the bound the compiler would otherwise infer comes from
the first 1D array parameter (w.length, the number of SETS) -- a
different count whenever sets and elements aren't equinumerous.

S, w and G are program fields, so the aux methods read them directly
rather than taking them as parameters.

Three cached quantities keep the predicate off the critical path. The set
prices sp[] are maintained by the advance, so setTight is O(1) instead of
an O(u) sum; slack[] records which elements lie in no tight set; and
minSlackIn[s] is the smallest slack element of s, which turns "no slack
element below x shares a set with x" into a scan of the sets containing x
rather than a scan over all smaller elements, each re-deciding slackness.
All three are rebuilt only after an advance has moved a price.

PRECONDITION: every element must lie in at least one set, or no cover
exists at all. The guard enforces it rather than assuming it, because
an uncoverable element is slack forever -- it lies in no tight set, so
nothing can ever make it non-slack -- and the fixpoint marks a sweep
as progress whenever a rule fires, not when G actually moves. Without
the covered(x) conjunct below, one such element makes this program
raise its price forever and never terminate. With it, an uncoverable
element simply keeps price 0 and the sets returned cover everything
that can be covered.

Auto-generated from LLPWeightedSetCover.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPWeightedSetCover:
    def LLPWeightedSetCover(self, mem, inc, w):
        self.mem = mem
        self.inc = inc
        self.w = w
        self.n = len(self.mem)
        self.m = len(self.w)
        self.u = len(self.inc)
        self.G = [0.0] * (self.u)
        self.sp = [0.0] * (self.m)
        self.slack = [False] * (self.u)
        self.minSlackIn = [0] * (self.m)
        self.fresh = False
        _changed = True
        while _changed:
            _changed = False
            for x in range(0, ((self.u - 1)) + 1):
                if (self.covered(x) and self.slackMin(x)):
                    step = self.minRemainingBudget(x)
                    self.G[x] = (self.G[x] + step)
                    t = 0
                    while (t < len(self.inc[x])):
                        self.sp[self.inc[x][t]] = (self.sp[self.inc[x][t]] + step)
                        t = (t + 1)
                    self.fresh = False
                    _changed = True
        C = [False] * (self.m)
        for s in range(0, ((self.m - 1)) + 1):
            C[s] = self.setTight(s)
        return C

    def covered(self, x):
        return (len(self.inc[x]) > 0)

    def setTight(self, s):
        return (self.sp[s] >= self.w[s])

    def refresh(self):
        if (not self.fresh):
            for e in range(0, ((self.u - 1)) + 1):
                self.slack[e] = self.isSlack(e)
            for s in range(0, ((self.m - 1)) + 1):
                self.minSlackIn[s] = self.firstSlackOf(s)
            self.fresh = True

    def firstSlackOf(self, s):
        k = 0
        while (k < len(self.mem[s])):
            if self.slack[self.mem[s][k]]:
                return self.mem[s][k]
            k = (k + 1)
        return self.u

    def isSlack(self, e):
        return (not any(self.setTight(s) for s in self.inc[e]))

    def minRemainingBudget(self, e):
        best = float('inf')
        for s in self.inc[e]:
            budget = (self.w[s] - self.sp[s])
            if (budget < best):
                best = budget
        return best

    def slackMin(self, x):
        self.refresh()
        if (not self.slack[x]):
            return False
        k = 0
        while (k < len(self.inc[x])):
            if (self.minSlackIn[self.inc[x][k]] < x):
                return False
            k = (k + 1)
        return True


if __name__ == '__main__':
    mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    w = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    prog = LLPWeightedSetCover()
    result = prog.LLPWeightedSetCover(mem, inc, w)
    print(result)

