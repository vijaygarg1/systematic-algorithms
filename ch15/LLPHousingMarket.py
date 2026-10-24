"""LLP housing market, matching bxx-housing.tex Algorithm
LLP-Housing-Market-Algorithm. Houses are identified with agent
indices (agent j initially owns house j). wish(i) is the house agent
i currently proposes for. S(G), the largest submatching, is the set
of agents lying on a cycle of the wish functional graph (i -> wish(i))
-- exactly the agents who can all trade among themselves. forbidden:
agent j is not itself in S(G) but wishes for a house held by an agent
who is; advance moves j to its next preference.

S(G) is built for the whole market at once, which is what makes the
round cost O(n) and the algorithm O(n^2), as the chapter states: every
agent wishes for exactly one house, so the wish graph is functional
and its cycles are precisely what survives repeatedly discarding the
agents nobody wishes for. Asking the question one agent at a time
instead -- following wishes from j until they return to j -- answers
it in O(n) per agent and so costs O(n^2) per round, a full factor of n
too much. inS[] is rebuilt only after an advance has moved a wish.

Earlier version of this file (kept for reference, not used): treated
an agent as "in the submatching" whenever no other agent currently
wished for the same house -- i.e. current-wish uniqueness -- rather
than genuine cycle membership in the wish graph. This is a different,
weaker condition: on the book's own running example (G=[0,0,0,0],
wishes 0->1, 1->0, 2->0, 3->1, 0-indexed), the old inSubmatching(0,...)
returned false (since agent 3 also wishes for house 1... texts differ
per index, see the book's own trace) where the book states agents
{1,2} (1-indexed) are in S(G) via the mutual wish-cycle 0<->1.

class LLPHousingMarket {
int[] LLPHousingMarket(int[][] pref) {
int n = pref.length;
int[] G = new int[n];
forall k in [0..n-1] : G[k] = 0;
forbidden (j) : !inSubmatching(j, G, pref) && wishInSubmatching(j, G, pref) =>
advance : G[j] = G[j] + 1;
return G;
}

boolean inSubmatching(int j, int[] G, int[][] pref) {
int n = G.length;
int target = pref[j][G[j]];
int i = 0;
while (i < n) {
if (i != j && pref[i][G[i]] == target) {
return false;
};
i = i + 1;
};
return true;
}

boolean wishInSubmatching(int j, int[] G, int[][] pref) {
int n = G.length;
int wish = pref[j][G[j]];
int i = 0;
while (i < n) {
if (pref[i][G[i]] == wish && inSubmatching(i, G, pref)) {
return true;
};
i = i + 1;
};
return false;
}
}

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
            for j in range(0, (self.n - 1) + 1):
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

