"""LLP housing market, matching bxx-housing.tex Algorithm
LLP-Housing-Market-Algorithm. Houses are identified with agent
indices (agent j initially owns house j). wish(i) is the house agent
i currently proposes for. S(G), the largest submatching, is the set
of agents lying on a cycle of the wish functional graph (i -> wish(i))

Auto-generated from LLPHousingMarket.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPHousingMarket:
    def LLPHousingMarket(self, pref):
        self.pref = pref
        self.n = len(self.pref)
        self.G = [0] * (self.n)
        self.inS = [False] * (self.n)
        self.indeg = [0] * (self.n)
        self.q = [0] * (self.n)
        self.fresh = False
        for k in range(0, ((self.n - 1)) + 1):
            self.G[k] = 0
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if ((not self.inSubmatching(j, self.G, self.pref)) and self.inSubmatching(self.wish(j, self.G, self.pref), self.G, self.pref)):
                    self.G[j] = (self.G[j] + 1)
                    self.fresh = False
                    _changed = True
        return self.G

    def wish(self, i, G, pref):
        return pref[i][G[i]]

    def inSubmatching(self, j, G, pref):
        self.refresh(G, pref)
        return self.inS[j]

    def refresh(self, G, pref):
        if self.fresh:
            return
        for i in range(0, ((self.n - 1)) + 1):
            self.inS[i] = True
            self.indeg[i] = 0
        for i in range(0, ((self.n - 1)) + 1):
            self.indeg[self.wish(i, G, pref)] = (self.indeg[self.wish(i, G, pref)] + 1)
        qt = 0
        for i in range(0, ((self.n - 1)) + 1):
            if (self.indeg[i] == 0):
                self.q[qt] = i
                qt = (qt + 1)
        qh = 0
        while (qh < qt):
            x = self.q[qh]
            qh = (qh + 1)
            self.inS[x] = False
            y = self.wish(x, G, pref)
            self.indeg[y] = (self.indeg[y] - 1)
            if (self.indeg[y] == 0):
                self.q[qt] = y
                qt = (qt + 1)
        self.fresh = True


if __name__ == '__main__':
    pref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPHousingMarket()
    result = prog.LLPHousingMarket(pref)
    print(result)

