"""LLP form of Johnson reweighting: raise prices G[j] until every reduced edge weight is non-negative.

The graph is a weighted adjacency list of PREDECESSORS: pre[j] holds the
tails of the edges into j and the parallel pw[j] their weights, so the
weight of (pre[j][t], j) is pw[j][t]. No n x n matrix is involved, so the
whole instance is O(n + m). The matrix form cost Theta(n^2) to hold and
read, which put a floor under any bound expressed in m.

The book states O(mn) for this program (bxx-sp-llp-johnson.tex:38: "Each
LLP iteration can be computed in O(m) time, so the graph is reweighted in
O(mn) time"). The O(nm log n) of the chapter summary table is for the
COMBINED all-pairs method -- this reweighting followed by Dijkstra from
every source -- which this program does not do; its output is the price
vector, as the algorithm box's KwOut says.

Written inline, as Algorithm LLP-Johnson states it:
Forbidden (j in [0..n-1]): exists i in pre(j): G[j] < G[i] - w(i,j)
Advance : forall k: G[k] := max(0, max { G[i] - w(i,k) | i in pre(k) })

The book's "exists i in pre(j) ... w(i,j)" is spelled over POSITIONS
here: pre[j][t] is the t-th predecessor of j and pw[j][t] the weight of
that edge. Both are O(m); an n x n weight matrix would cost Theta(n^2)
before the algorithm started (see the note in the chapter-7 Dijkstra
section on why the book writes w(i,j) and not w[i,j]).

max over an empty range is the identity -INF, so a vertex with no
predecessors takes max(0, -INF) = 0 -- which is the book's max(0, ...)
with no special case.

Auto-generated from LLPJohnson.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPJohnson:
    def LLPJohnson(self, pre, pw):
        self.pre = pre
        self.pw = pw
        self.n = len(self.pre)
        self.G = [0] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if any((self.G[j] < (self.G[self.pre[j][t]] - self.pw[j][t])) for t in range(0, ((len(self.pre[j]) - 1)) + 1)):
                    for k in range(0, ((self.n - 1)) + 1):
                        self.G[k] = max(0, max(((self.G[self.pre[k][t]] - self.pw[k][t]) for t in range(0, ((len(self.pre[k]) - 1)) + 1)), default=-2147483647))
                    _changed = True
        return self.G


if __name__ == '__main__':
    pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]]
    pw = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPJohnson()
    result = prog.LLPJohnson(pre, pw)
    print(result)

