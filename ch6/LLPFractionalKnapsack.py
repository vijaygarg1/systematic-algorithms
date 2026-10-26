"""LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each
fraction G[j] in [0,1] to its target G*[j], read in O(1) from one
prefix scan of the weights.

Auto-generated from LLPFractionalKnapsack.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPFractionalKnapsack:
    def LLPFractionalKnapsack(self, v, w, W):
        self.v = v
        self.w = w
        self.W = W
        self.n = len(self.v)
        self.G = [0.0] * (self.n)
        self.S = [0.0] * ((self.n + 1))
        self.i = 0
        while (self.i < self.n):
            self.S[(self.i + 1)] = (self.S[self.i] + self.w[self.i])
            self.i = (self.i + 1)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (self.G[j] < self.target(j)):
                    self.G[j] = self.target(j)
                    _changed = True
        return self.G

    def target(self, j):
        if (self.S[(j + 1)] <= self.W):
            return 1.0
        if (self.S[j] >= self.W):
            return 0.0
        return (self.W - self.S[j]) / self.w[j]


if __name__ == '__main__':
    v = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    w = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    W = 10.0
    prog = LLPFractionalKnapsack()
    result = prog.LLPFractionalKnapsack(v, w, W)
    print(result)

