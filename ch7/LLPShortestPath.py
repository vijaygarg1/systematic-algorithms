"""LLP-ShortestPath: Dijkstra-style LLP with priority-queue scheduling.
always: parent[j][i] = (i in pre[j]) && (G[j] >= G[i] + w[i][j])
always: fixed[j] = (j == s) || (exists i : parent[j][i] && fixed[i])

Auto-generated from LLPShortestPath.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPShortestPath:
    def LLPShortestPath(self, s, pre, w):
        self.s = s
        self.pre = pre
        self.w = w
        self.n = len(self.pre)
        self.G = [0] * (self.n)
        self.fixed = [False] * (self.n)
        self.fixed[self.s] = True
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if (not self.fixed[j]):
                    self.G[j] = self.minCrossCut(j, self.pre, self.w, self.G, self.fixed)
                    self.fixed[j] = True
                    _changed = True

    def minCrossCut(self, j, pre, w, G, fixed):
        best = 2147483647
        k = 0
        while (k < len(pre[j])):
            i = pre[j][k]
            if fixed[i]:
                d = (G[i] + w[i][j])
                if (d < best):
                    best = d
            k = (k + 1)
        return best


if __name__ == '__main__':
    pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]]
    w = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPShortestPath()
    prog.LLPShortestPath(0, pre, w)
    print(pre)
    print(w)

