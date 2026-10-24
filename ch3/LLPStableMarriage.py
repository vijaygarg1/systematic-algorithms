"""LLP-StableMarriage: Gale-Shapley as a lattice-linear program, exactly
as bxx-stableMatching.tex's Algorithm LLP-StableMarriage states it.

Always    (i): z = mpref[i][G[i]]
Forbidden (i): (G[i] = 0) or ((partner[z] != null) and
(rank[z][partner[z]] < rank[z][i]))
Advance      : G[i] := G[i] + 1;
if ((partner[z] = null) or (rank[z][i] < rank[z][partner[z]])):
partner[z] := i

G[i] is a PROPOSAL COUNT: how many proposals man i has made, 0..n. The
book's arrays are 1-indexed, so its z = mpref[i][G[i]] is the proposal
man i is currently standing on; mpref here is 0-indexed, so the same
woman is mpref[i][G[i]-1], and G[i] = 0 means he has proposed to
nobody yet (the guard's first disjunct, which short-circuits before z
is ever needed).

z is written out at each use rather than declared with LL's `always`.
The book's Always is a definition, re-read wherever it appears, and
the advance reads it AFTER incrementing G[i] -- man i proposes to his
NEXT choice. llc.py's `always` instead binds once on entry to each of
forbidden() and advance(), so the advance would propose to the woman
who had just rejected him.

partner[z] (-1 if unmatched) is the best man who has proposed to z so
far. It is redundant state, recoverable from G in O(n), and exists
only so the guard is O(1): LLPStableMarriageSpec.llp is the same
algorithm with the guard rescanning every man's whole proposal
history instead, and the book derives this program from that one.

Auto-generated from LLPStableMarriage.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPStableMarriage:
    def LLPStableMarriage(self, mpref, rank):
        self.mpref = mpref
        self.rank = rank
        self.n = len(self.mpref)
        self.G = [0] * (self.n)
        self.partner = [0] * (self.n)
        for z in range(0, ((self.n - 1)) + 1):
            self.partner[z] = (-1)
        _changed = True
        while _changed:
            _changed = False
            for i in range(0, (self.n - 1) + 1):
                if ((self.G[i] == 0) or self.rejected(i)):
                    self.G[i] = (self.G[i] + 1)
                    z = self.mpref[i][(self.G[i] - 1)]
                    if ((self.partner[z] == (-1)) or (self.rank[z][i] < self.rank[z][self.partner[z]])):
                        self.partner[z] = i
                    _changed = True
        return self.G

    def rejected(self, i):
        z = self.mpref[i][(self.G[i] - 1)]
        return ((self.partner[z] != (-1)) and (self.rank[z][self.partner[z]] < self.rank[z][i]))


if __name__ == '__main__':
    mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = LLPStableMarriage()
    result = prog.LLPStableMarriage(mpref, rank)
    print(result)

