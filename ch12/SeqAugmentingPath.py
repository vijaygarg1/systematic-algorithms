"""Classical augmenting-path bipartite matching, matching
bxx-matchingReduced.tex Algorithm Seq-AugmentingPath (algo:seq-bipartite):
for each L-vertex j in turn, look for an alternating path to some free
R-vertex k (S[k] unmatched). The book's abstract "there exists an
alternating path..." step, plus its "assign S[] along the path"

Auto-generated from SeqAugmentingPath.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class SeqAugmentingPath:
    def SeqAugmentingPath(self, adj, nr):
        self.adj = adj
        self.nr = nr
        self.n = len(self.adj)
        self.m = self.nr
        self.G = [0] * (self.n)
        self.S = [0] * (self.m)
        for j in range(0, ((self.n - 1)) + 1):
            self.G[j] = 0
        for k in range(0, ((self.m - 1)) + 1):
            self.S[k] = (0 - 1)
        for j in range(0, ((self.n - 1)) + 1):
            seen = [False] * (self.m)
            if self.alternatingPath(j, self.adj, self.S, seen):
                self.G[j] = 1
        return self.G

    def alternatingPath(self, j, adj, S, seen):
        t = 0
        while (t < len(adj[j])):
            k = adj[j][t]
            if (not seen[k]):
                seen[k] = True
                if ((S[k] == (0 - 1)) or self.alternatingPath(S[k], adj, S, seen)):
                    S[k] = j
                    return True
            t = (t + 1)
        return False


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = SeqAugmentingPath()
    result = prog.SeqAugmentingPath(adj, len(adj))
    print(result)

