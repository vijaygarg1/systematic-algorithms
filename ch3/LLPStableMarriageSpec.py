"""LLP-StableMarriage-Spec: specification form of Gale-Shapley (bxx-stableMatching.tex).
G[j] = man j's current proposal index, 1 = top choice; I is its initial value, 1<=I[i]<=n.
Guard restored 2026-10-26: without it a bad I runs G[j] to n+1 and reads past mpref's row.

Auto-generated from LLPStableMarriageSpec.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPStableMarriageSpec:
    def LLPStableMarriageSpec(self, mpref, rank, I):
        self.mpref = mpref
        self.rank = rank
        self.I = I
        self.n = len(self.mpref) - 1
        self.G = list(self.I)
        _changed = True
        while _changed:
            _changed = False
            for j in range(1, (self.n) + 1):
                z = self.mpref[j][self.G[j]]
                if any(any(((self.mpref[i][k] == z) and (self.rank[z][i] < self.rank[z][j])) for k in range(1, (self.G[i]) + 1)) for i in range(1, (self.n) + 1)):
                    if (self.G[j] == self.n):
                        return None
                    self.G[j] = (self.G[j] + 1)
                    _changed = True
        return self.G


if __name__ == '__main__':
    mpref = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 2, 3, 4, 5, 6, 7], [0, 2, 3, 4, 5, 6, 7, 1], [0, 3, 4, 5, 6, 7, 1, 2], [0, 4, 5, 6, 7, 1, 2, 3], [0, 5, 6, 7, 1, 2, 3, 4], [0, 6, 7, 1, 2, 3, 4, 5], [0, 7, 1, 2, 3, 4, 5, 6]]
    rank = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 7, 6, 5, 4, 3, 2], [0, 2, 1, 7, 6, 5, 4, 3], [0, 3, 2, 1, 7, 6, 5, 4], [0, 4, 3, 2, 1, 7, 6, 5], [0, 5, 4, 3, 2, 1, 7, 6], [0, 6, 5, 4, 3, 2, 1, 7], [0, 7, 6, 5, 4, 3, 2, 1]]
    I = [0, 1, 1, 1, 1, 1, 1, 1]
    prog = LLPStableMarriageSpec()
    result = prog.LLPStableMarriageSpec(mpref, rank, I)
    print(result)

