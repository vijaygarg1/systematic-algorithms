"""LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge.

Edges are ordered by (min(u,v), max(u,v)), and the lex-least uncovered edge
incident to a vertex u is always {u, minNbr(u)}, where minNbr(u) is u's
smallest uncovered neighbour: a neighbour y < u contributes the key (y, u)
and a neighbour y > u the key (u, y), so in both cases the smallest index
wins. An edge is therefore lex-minimal among the edges touching it exactly
when it is the lex-least edge at BOTH of its endpoints, which is what
isLexMin below tests with two O(n) scans. Testing it directly -- "no smaller
uncovered edge touches i or j" -- costs an O(n^2) scan over all edge pairs
per candidate, so the predicate alone dominated the whole run.

Auto-generated from LLPLexMatchVertexCover.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPLexMatchVertexCover:
    def LLPLexMatchVertexCover(self, adj):
        self.adj = adj
        self.n = len(self.adj)
        self.G = [False] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self.isLexMin(j):
                    i = self.minNbr(j)
                    self.G[j] = True
                    self.G[i] = True
                    _changed = True
        return self.G

    def isLexMin(self, j):
        if self.G[j]:
            return False
        i = self.minNbr(j)
        if (i < 0):
            return False
        return (self.minNbr(i) == j)

    def minNbr(self, u):
        k = 0
        while (k < len(self.adj[u])):
            if (not self.G[self.adj[u][k]]):
                return self.adj[u][k]
            k = (k + 1)
        return (0 - 1)


if __name__ == '__main__':
    adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPLexMatchVertexCover()
    result = prog.LLPLexMatchVertexCover(adj)
    print(result)

