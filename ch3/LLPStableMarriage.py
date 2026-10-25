"""LLP-StableMarriage: Gale-Shapley as a lattice-linear program, written
exactly as bxx-stableMatching.tex's Algorithm LLP-StableMarriage:

Var          : partner: array[1..n] of int initially 0
Always       : z = mpref[i][G[i]]
Forbidden (i): (G[i] = 0) or ((partner[z] != 0) and
(rank[z][partner[z]] < rank[z][i]))
Advance      : G[i] := G[i] + 1;
if ((partner[z] = 0) or (rank[z][i] < rank[z][partner[z]]))
then partner[z] := i

setBase(1) and the [0..n] declarations give the book's array[1..n]:
every array is n+1 long and slot 0 is never used, so the indices are
1..n and `mpref[i][G[i]]` is the book's expression with no offset. That
is also what makes G[i] = 0 safe to read -- it lands on the unused slot
0, and the guard's first disjunct makes i forbidden regardless.

G[i] is the book's position in man i's preference list, counted from 1:
0 means he has proposed to nobody, k that he is standing on his k-th
choice. LLPStableMarriageSpec.llp uses the same G.

partner[z] = 0 means woman z is free: 0 is not a woman, because women
are 1..n. The book uses 0 here too.

The book's Always is a definition, re-read wherever it appears, so the
advance's z is the NEXT choice -- read after G[i] is incremented. The
compiler now matches that: it binds the always variable on entry and
re-assigns it after any statement writing something the expression
reads, so no explicit re-read is written here (see _refresh_always in
llc.py).

partner[z] is the best man who has proposed to z so far: redundant
state, recoverable from G in O(n), kept only so the guard costs O(1)
rather than the Spec's rescan of every man's proposal history.

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

