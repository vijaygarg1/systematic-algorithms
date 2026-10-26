"""LLP-StableMarriage: Gale-Shapley as a lattice-linear program, written
exactly as bxx-stableMatching.tex's Algorithm LLP-StableMarriage:

Auto-generated from LLPStableMarriage.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPStableMarriage:
    def LLPStableMarriage(self, mpref, rank):
        self.mpref = mpref
        self.rank = rank
        self.n = len(self.mpref) - 1
        self.G = [0] * (self.n + 1)
        self.partner = [0] * (self.n + 1)
        _changed = True
        while _changed:
            _changed = False
            for i in range(1, (self.n) + 1):
                z = self.mpref[i][self.G[i]]
                if ((self.G[i] == 0) or ((self.partner[z] != 0) and (self.rank[z][self.partner[z]] < self.rank[z][i]))):
                    self.G[i] = (self.G[i] + 1)
                    z = self.mpref[i][self.G[i]]
                    if ((self.partner[z] == 0) or (self.rank[z][i] < self.rank[z][self.partner[z]])):
                        self.partner[z] = i
                    _changed = True
        return self.G


if __name__ == '__main__':
    mpref = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 2, 3, 4, 5, 6, 7], [0, 2, 3, 4, 5, 6, 7, 1], [0, 3, 4, 5, 6, 7, 1, 2], [0, 4, 5, 6, 7, 1, 2, 3], [0, 5, 6, 7, 1, 2, 3, 4], [0, 6, 7, 1, 2, 3, 4, 5], [0, 7, 1, 2, 3, 4, 5, 6]]
    rank = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 7, 6, 5, 4, 3, 2], [0, 2, 1, 7, 6, 5, 4, 3], [0, 3, 2, 1, 7, 6, 5, 4], [0, 4, 3, 2, 1, 7, 6, 5], [0, 5, 4, 3, 2, 1, 7, 6], [0, 6, 5, 4, 3, 2, 1, 7], [0, 7, 6, 5, 4, 3, 2, 1]]
    prog = LLPStableMarriage()
    result = prog.LLPStableMarriage(mpref, rank)
    print(result)

