"""LLP-Knapsack: the book's two rules over the (i, c) lattice, joined by
`&&` so they share one fixpoint, with each index carrying its own
bound -- i over items, c over capacities.

Auto-generated from LLPKnapsack.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPKnapsack:
    def LLPKnapsack(self, w, v, W):
        self.w = w
        self.v = v
        self.W = W
        self.n = (len(self.w) - 1)
        self.G = [[0] * ((self.W + 1)) for _ in range((self.n + 1))]
        _changed = True
        while _changed:
            _changed = False
            for i in range(1, (self.n) + 1):
                for c in range(1, (self.W) + 1):
                    if (self.G[i][c] < self.G[(i - 1)][c]):
                        self.G[i][c] = self.G[(i - 1)][c]
                        _changed = True
                    if ((self.w[i] <= c) and (self.G[i][c] < (self.G[(i - 1)][(c - self.w[i])] + self.v[i]))):
                        self.G[i][c] = (self.G[(i - 1)][(c - self.w[i])] + self.v[i])
                        _changed = True
        return self.G


if __name__ == '__main__':
    w = [0, 0, 1, 2]
    v = [0, 0, 1, 1]
    W = 10
    prog = LLPKnapsack()
    result = prog.LLPKnapsack(w, v, W)
    print(result)

