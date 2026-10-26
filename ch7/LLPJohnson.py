"""LLP form of Johnson reweighting: raise prices G[j] until every reduced edge weight is non-negative.

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
                if self.reducedNegative(j):
                    for k in range(0, ((self.n - 1)) + 1):
                        self.G[k] = self.raised(k)
                    _changed = True
        return self.G

    def reducedNegative(self, j):
        t = 0
        while (t < len(self.pre[j])):
            if (self.G[j] < (self.G[self.pre[j][t]] - self.pw[j][t])):
                return True
            t = (t + 1)
        return False

    def raised(self, k):
        best = self.G[k]
        t = 0
        while (t < len(self.pre[k])):
            cand = (self.G[self.pre[k][t]] - self.pw[k][t])
            if (cand > best):
                best = cand
            t = (t + 1)
        return best


if __name__ == '__main__':
    pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]]
    pw = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPJohnson()
    result = prog.LLPJohnson(pre, pw)
    print(result)

