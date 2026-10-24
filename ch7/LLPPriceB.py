"""Price_b: find the minimum price vector using LLP.
forbidden(j): exists predecessor i with p[j] < p[i] - w[i,j].
advance: p[j] := max { p[i] - w[i,j] | i in pre(j) }.

Auto-generated from LLPPriceB.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPPriceB:
    def LLPPriceB(self, pre, w):
        self.pre = pre
        self.w = w
        self.n = len(self.pre)
        self.G = [0] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if any((self.G[j] < (self.G[i] - self.w[i][j])) for i in self.pre[j]):
                    self.G[j] = self.maxPricePred(j, self.pre, self.w, self.G)
                    _changed = True

    def maxPricePred(self, j, pre, w, G):
        best = G[j]
        k = 0
        while (k < len(pre[j])):
            i = pre[j][k]
            val = (G[i] - w[i][j])
            if (val > best):
                best = val
            k = (k + 1)
        return best


if __name__ == '__main__':
    pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]]
    w = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPPriceB()
    prog.LLPPriceB(pre, w)
    print(pre)
    print(w)

