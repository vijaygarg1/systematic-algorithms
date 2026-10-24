"""LLP-StableMarriage: Gale-Shapley as a lattice-linear program, exactly
as bxx-stableMatching.tex's Algorithm LLP-StableMarriage states it.

Always    (i): z = mpref[i][G[i]]
Forbidden (i): (G[i] = 0) or ((partner[z] != null) and
(rank[z][partner[z]] < rank[z][i]))
Advance      : G[i] := G[i] + 1;
if ((partner[z] = null) or (rank[z][i] < rank[z][partner[z]]))
then partner[z] := i

G[i] is the book's POSITION in man i's preference list, counted from 1:
G[i] = 0 means he has proposed to nobody, G[i] = k that he is standing
on his k-th choice. LLPStableMarriageSpec.llp, which the book derives
this program from, uses the same G, so the two return the same vector.

Two places where the book's 1-indexed arrays do not carry over
verbatim, and nothing else differs:

The book's mpref is array[1..n][1..n], so its z = mpref[i][G[i]] is
the woman i is standing on, and G[i] = 0 harmlessly reads the unused
slot 0. Here mpref is 0-indexed, so the same woman is at G[i]-1 and
G[i] = 0 would index -1 -- hence max(G[i], 1) - 1, which is slot 0
when he has not yet proposed. The guard's first disjunct makes him
forbidden in that case regardless of what z holds.

The book's Always is a definition, re-read wherever it appears, so
the advance's z is the NEXT choice -- read after G[i] is
incremented. LL's `always` binds once on entry to each of forbidden
and advance, so the advance re-reads it explicitly.

partner[z] (-1 for null) is the best man who has proposed to z so far:
redundant state, recoverable from G in O(n), kept only so the guard is
O(1) rather than the Spec's rescan of every man's proposal history.

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
        for w in range(0, ((self.n - 1)) + 1):
            self.partner[w] = (-1)
        _changed = True
        while _changed:
            _changed = False
            for i in range(0, (self.n - 1) + 1):
                z = self.mpref[i][(max(self.G[i], 1) - 1)]
                if ((self.G[i] == 0) or ((self.partner[z] != (-1)) and (self.rank[z][self.partner[z]] < self.rank[z][i]))):
                    self.G[i] = (self.G[i] + 1)
                    z = self.mpref[i][(self.G[i] - 1)]
                    if ((self.partner[z] == (-1)) or (self.rank[z][i] < self.rank[z][self.partner[z]])):
                        self.partner[z] = i
                    _changed = True
        return self.G


if __name__ == '__main__':
    mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = LLPStableMarriage()
    result = prog.LLPStableMarriage(mpref, rank)
    print(result)

