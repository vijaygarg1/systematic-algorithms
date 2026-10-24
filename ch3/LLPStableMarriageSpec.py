"""LLP-StableMarriage-Spec: a specification of Gale-Shapley, not yet an
efficient algorithm (bxx-stableMatching.tex, Algorithm
LLP-StableMarriage-Spec). Its forbidden condition rescans every man's
whole proposal history to find out who currently holds a given
woman -- O(n) per guard, O(n^4) total -- because an arbitrary initial
G gives no cheaper way to tell. It exists only as a correctness
stepping stone toward LLPStableMarriage.llp, which caches that
information instead; do not measure or recommend this version on its
own (see v3performance.txt).

Auto-generated from LLPStableMarriageSpec.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPStableMarriageSpec:
    def LLPStableMarriageSpec(self, mpref, rank, I):
        self.mpref = mpref
        self.rank = rank
        self.I = I
        self.n = len(self.mpref)
        self.G = list(self.I)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if any(any(((self.mpref[j][self.G[j]] == self.mpref[i][k]) and (self.rank[self.mpref[j][self.G[j]]][i] < self.rank[self.mpref[j][self.G[j]]][j])) for k in range(0, (self.G[i]) + 1)) for i in range(0, ((self.n - 1)) + 1)):
                    self.G[j] = (self.G[j] + 1)
                    _changed = True
        return self.G


if __name__ == '__main__':
    mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    I = [0, 0, 0, 0, 0, 0, 0, 0]
    prog = LLPStableMarriageSpec()
    result = prog.LLPStableMarriageSpec(mpref, rank, I)
    print(result)

