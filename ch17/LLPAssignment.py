"""LLP-Assignment (bxx-assignment.tex, algo:llp-assignment): forbidden when
item j belongs to an inclusion-minimal overdemanded set J -- found by
alternating-path reachability from an unmatched bidder, the standard
witness for a violated Hall condition. Advance raises EVERY item in J by
one shared slack jump, delta := min over bidders demanding into J of

Auto-generated from LLPAssignment.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPAssignment:
    def LLPAssignment(self, v):
        self.v = v
        self.n = len(self.v[0])
        self.m = len(self.v)
        self.G = [0] * (self.n)
        self.partner = [0] * (self.n)
        self.itemInJ = [False] * (self.n)
        self.bidderInB = [False] * (self.m)
        self.fresh = False
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self.overdemanded(j):
                    self.raiseJ()
                    _changed = True
        return self.G

    def overdemanded(self, j):
        self.refresh()
        return self.itemInJ[j]

    def refresh(self):
        if self.fresh:
            return
        for i in range(0, ((self.n - 1)) + 1):
            self.itemInJ[i] = False
            self.partner[i] = (0 - 1)
        for b in range(0, ((self.m - 1)) + 1):
            self.bidderInB[b] = False
        for b in range(0, ((self.m - 1)) + 1):
            self.tryMatch(b, [False] * (self.n))
        u = self.firstUnmatched()
        if (u < self.m):
            self.reach(u)
        self.fresh = True

    def raiseJ(self):
        delta = 2147483647
        for b in range(0, ((self.m - 1)) + 1):
            if self.bidderInB[b]:
                drop = (self.bestSurplus(b) - self.bestOutside(b))
                if (drop < delta):
                    delta = drop
        for i in range(0, ((self.n - 1)) + 1):
            if self.itemInJ[i]:
                self.G[i] = (self.G[i] + delta)
        self.fresh = False

    def firstUnmatched(self):
        for b in range(0, ((self.m - 1)) + 1):
            if (not any((self.partner[i] == b) for i in range(0, ((self.n - 1)) + 1))):
                return b
        return self.m

    def bestSurplus(self, b):
        return max(((self.v[b][i] - self.G[i]) for i in range(0, ((self.n - 1)) + 1)), default=-2147483647)

    def bestOutside(self, b):
        best = (0 - 2147483647)
        for i in range(0, ((self.n - 1)) + 1):
            if ((not self.itemInJ[i]) and ((self.v[b][i] - self.G[i]) > best)):
                best = (self.v[b][i] - self.G[i])
        return best

    def reach(self, b):
        if self.bidderInB[b]:
            return
        self.bidderInB[b] = True
        best = self.bestSurplus(b)
        for i in range(0, ((self.n - 1)) + 1):
            if (((self.v[b][i] - self.G[i]) == best) and (not self.itemInJ[i])):
                self.itemInJ[i] = True
                if (self.partner[i] != (0 - 1)):
                    self.reach(self.partner[i])

    def tryMatch(self, b, seen):
        best = self.bestSurplus(b)
        i = 0
        while (i < self.n):
            if (((self.v[b][i] - self.G[i]) == best) and (not seen[i])):
                seen[i] = True
                if ((self.partner[i] == (0 - 1)) or self.tryMatch(self.partner[i], seen)):
                    self.partner[i] = b
                    return True
            i = (i + 1)
        return False


if __name__ == '__main__':
    v = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPAssignment()
    result = prog.LLPAssignment(v)
    print(result)

