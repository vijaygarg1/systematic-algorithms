"""LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge.

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

