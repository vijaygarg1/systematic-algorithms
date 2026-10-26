"""Koenig's theorem: minimum vertex cover of size |M| from a maximum
bipartite matching, via alternating reachability. Matches
bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set
of vertices reachable from an unmatched L-vertex by an alternating path
(non-matching edge, then matching edge, ...); C := (L \ Z) union (R

Auto-generated from VertexCoverFromMatching.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class VertexCoverFromMatching:
    def VertexCoverFromMatching(self, adj, nr, matchL):
        self.adj = adj
        self.nr = nr
        self.matchL = matchL
        self.n = len(self.adj)
        self.L = len(self.adj)
        self.R = self.nr
        self.matchR = [0] * (self.R)
        for v in range(0, ((self.R - 1)) + 1):
            self.matchR[v] = (0 - 1)
        for j in range(0, ((self.L - 1)) + 1):
            if (self.matchL[j] != (0 - 1)):
                self.matchR[self.matchL[j]] = j
        inZ = [False] * ((self.L + self.R))
        Q = [0] * ((self.L + self.R))
        head = 0
        tail = 0
        for j in range(0, ((self.L - 1)) + 1):
            if (self.matchL[j] == (0 - 1)):
                inZ[j] = True
                Q[tail] = j
                tail = (tail + 1)
        while (head < tail):
            w = Q[head]
            head = (head + 1)
            if (w < self.L):
                t = 0
                while (t < len(self.adj[w])):
                    v = self.adj[w][t]
                    if ((self.matchL[w] != v) and (not inZ[(self.L + v)])):
                        inZ[(self.L + v)] = True
                        Q[tail] = (self.L + v)
                        tail = (tail + 1)
                    t = (t + 1)
            else:
                r = (w - self.L)
                if ((self.matchR[r] != (0 - 1)) and (not inZ[self.matchR[r]])):
                    inZ[self.matchR[r]] = True
                    Q[tail] = self.matchR[r]
                    tail = (tail + 1)
        C = [False] * ((self.L + self.R))
        for j in range(0, ((self.L - 1)) + 1):
            C[j] = (not inZ[j])
        for v in range(0, ((self.R - 1)) + 1):
            C[(self.L + v)] = inZ[(self.L + v)]
        return C


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    matchL = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = VertexCoverFromMatching()
    result = prog.VertexCoverFromMatching(adj, len(adj), matchL)
    print(result)

