"""Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements.

Follows Algorithm ApproxSetCover in bxx-approximate.tex, including its
stated precondition: the instance is assumed feasible, i.e. every
element of the universe lies in some set, so that a cover exists. The
loop guard is the book's `R != empty`, not a weaker "some set still
covers something new" test -- on an infeasible instance it would not
terminate, exactly as the book's algorithm would not.

The uncovered counts cnt[s] = |s ∩ R| are maintained instead of being
recomputed each round: covering an element decrements the count of every
set containing it, so the total number of updates over the whole run is
one per (set, element) incidence. Recomputing all m counts in each of the
up-to min(m,n) rounds costs a factor min(m,n) more.

The instance is a pair of incidence lists: mem[s] holds the elements of
set s and inc[e] holds the sets containing element e. Neither is an
m x u matrix, so the whole instance is O(N) -- N being the number of
(set, element) incidences -- rather than Theta(m*u). Every loop below
walks one of these lists, so each pass costs O(N) and not O(m*u).

Taking them as input rather than building them from a matrix is what
makes that real: an m x u matrix had to be read once to build the lists,
which is Theta(m*u) on its own and reached a gigabyte at n = 16000.

Auto-generated from ApproxSetCover.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ApproxSetCover:
    def ApproxSetCover(self, mem, inc):
        self.mem = mem
        self.inc = inc
        self.m = len(self.mem)
        self.n = len(self.inc)
        self.C = [False] * (self.m)
        self.covered = [False] * (self.n)
        self.cnt = [0] * (self.m)
        self.remaining = self.n
        for s in range(0, ((self.m - 1)) + 1):
            self.cnt[s] = len(self.mem[s])
        while (self.remaining > 0):
            bestIdx = 0
            bestCover = (0 - 1)
            for s in range(0, ((self.m - 1)) + 1):
                if (self.cnt[s] > bestCover):
                    bestCover = self.cnt[s]
                    bestIdx = s
            self.C[bestIdx] = True
            for k in range(0, ((len(self.mem[bestIdx]) - 1)) + 1):
                e = self.mem[bestIdx][k]
                if (not self.covered[e]):
                    self.covered[e] = True
                    self.remaining = (self.remaining - 1)
                    t = 0
                    while (t < len(self.inc[e])):
                        self.cnt[self.inc[e][t]] = (self.cnt[self.inc[e][t]] - 1)
                        t = (t + 1)
        return self.C


if __name__ == '__main__':
    mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = ApproxSetCover()
    result = prog.ApproxSetCover(mem, inc)
    print(result)

