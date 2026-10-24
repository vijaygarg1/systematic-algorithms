"""LLP-StableMarriage: Gale-Shapley via a cached partner[] vector, giving
O(1) guards instead of LLPStableMarriageSpec's O(n) rescan over every
man's whole proposal history. Matches bxx-stableMatching.tex's
Algorithm LLP-StableMarriage (the optimization of LLP-StableMarriage-Spec).

G[i] is a PROPOSAL COUNT (how many proposals man i has made so far,
0..n) -- unlike LLPStableMarriageSpec.llp and progs-topicsStableMatching's
sibling programs, which use G[j] as a direct (0-indexed) index into
mpref[j]. The count form is what lets "forbidden" mean G[i]=0 with no
separate initial-proposal loop, matching the book's own derivation.
Since mpref is 0-indexed here but the count is 1-based once nonzero,
every mpref lookup is mpref[i][G[i]-1], not mpref[i][G[i]].

partner[z] (-1 if unmatched) caches the best man who has proposed to z
so far -- redundant state, recoverable from G in O(n), kept only so
the guard is O(1) instead of a rescan. It must be allocated and filled
via an explicit new int[n] + forall, not the "int[] partner = -1;"
shorthand used for plain all-zero fills elsewhere in this codebase:
without a following forall that copies another array's values into it
(as LLPStableMarriageSpec.llp's "int[] G = 0;" has), that shorthand
silently allocates a length-1 array instead of length n -- a real
compiler gap, not a style choice.

This is written procedurally with an explicit worklist, not as a
forbidden/advance pair, because llc.py's forbidden/advance always
realizes as either (a) a full O(n) rescan every round, repeated until
a round finds nothing to advance, or (b) with setContainer, a
PriorityQueue -- and PriorityQueue only re-pushes the index whose
*key* array changed, which here would be G, but the man a proposal
displaces (see "int y = partner[z]" below) needs re-checking too,
and his G does not change. (A dummy key array bumped for both the
proposer and the displaced man would work, but costs O(log n) per
heap operation for no reason -- plain FIFO order is already correct,
since nothing here depends on processing order, only on eventually
reconsidering every newly-forbidden man.) Option (a) measured O(n^3)
in practice (confirmed against this exact LCG-seeded generator, not
just asymptotically): the number of *rounds* to convergence is not
O(n) the way it is for Dijkstra-style settle-once algorithms, since a
displaced man can re-trigger forbidden() for men already scanned
earlier in the same round, pushing convergence out over many rounds.
A worklist sidesteps this entirely: only men who just became
forbidden (freshly rejected, or just displaced) are ever re-examined,
giving O(1) amortized work per check and a true O(n^2) worst case
bound (same precedent as EarlyFixingStableMarriage.llp's explicit
inX/queue arrays, also procedural for a similar reason).

The queue is a circular buffer of capacity n+1, not the n^2+1 a naive
worst-case bound on total pushes would suggest: inQueue[] stops a man
being enqueued twice at once, so at most n distinct men are ever
in-flight simultaneously, regardless of how many times each is
pushed over the full run. (An n^2-sized buffer was tried first and
exhausted heap memory well before n reached the sizes needed to
confirm the complexity fix.)

Cross-checked against LLPStableMarriageSpec.llp on instances with
cascading rejections: both return the same matching.

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
        cap = (self.n + 1)
        queue = [0] * (cap)
        inQueue = [False] * (self.n)
        qHead = 0
        qCount = 0
        qTail = 0
        for i in range(0, ((self.n - 1)) + 1):
            queue[qTail] = i
            qTail = ((qTail + 1) % cap)
            qCount = (qCount + 1)
            inQueue[i] = True
        while (qCount > 0):
            i = queue[qHead]
            qHead = ((qHead + 1) % cap)
            qCount = (qCount - 1)
            inQueue[i] = False
            isForbidden = False
            if (self.G[i] == 0):
                isForbidden = True
            else:
                z0 = self.mpref[i][(self.G[i] - 1)]
                if ((self.partner[z0] != (-1)) and (self.rank[z0][self.partner[z0]] < self.rank[z0][i])):
                    isForbidden = True
            if isForbidden:
                self.G[i] = (self.G[i] + 1)
                z = self.mpref[i][(self.G[i] - 1)]
                if ((self.partner[z] == (-1)) or (self.rank[z][i] < self.rank[z][self.partner[z]])):
                    y = self.partner[z]
                    self.partner[z] = i
                    if ((y != (-1)) and (not inQueue[y])):
                        queue[qTail] = y
                        qTail = ((qTail + 1) % cap)
                        qCount = (qCount + 1)
                        inQueue[y] = True
                if (not inQueue[i]):
                    queue[qTail] = i
                    qTail = ((qTail + 1) % cap)
                    qCount = (qCount + 1)
                    inQueue[i] = True
        return self.G


if __name__ == '__main__':
    mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = LLPStableMarriage()
    result = prog.LLPStableMarriage(mpref, rank)
    print(result)

