"""LLP-StableMarriage-Spec: the specification form of Gale-Shapley, exactly
as bxx-stableMatching.tex's Algorithm LLP-StableMarriage-Spec states it.

Always    (j): z = mpref[j][G[j]]
Forbidden (j): exists i in [1..n] : exists k in [1..G[i]] :
(z = mpref[i][k]) and (rank[z][i] < rank[z][j])
Advance      : G[j] := G[j] + 1

setBase(1) and the [0..n] declarations give the book's array[1..n]:
every array is n+1 long and slot 0 is never used, so the indices are
1..n and mpref[j][G[j]] is the book's expression with no offset.

G[j] is the book's position in man j's preference list, counted from 1,
and I is its initial value -- the book's standard initialization is
[1, 1, ..., 1], every man on his first choice. LLPStableMarriage.llp,
which the book derives from this program, uses the same G, so the two
return the same vector on the same instance.

The exists-exists scan is what makes this a specification rather than
an algorithm: an arbitrary initial G gives no way to tell from G alone
which woman currently holds the best proposal, so the guard re-examines
every man's whole proposal history. LLPStableMarriage.llp caches that
in partner[z] and tests it in O(1).

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

