"""0/1 knapsack: O(nW) bottom-up table fill.

Auto-generated from Knapsack.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Knapsack:
    def Knapsack(self, w, v, W):
        self.w = w
        self.v = v
        self.W = W
        self.n = (len(self.w) - 1)
        self.G = [[0] * ((self.W + 1)) for _ in range((self.n + 1))]
        for c in range(0, (self.W) + 1):
            self.G[0][c] = 0
        for i in range(0, (self.n) + 1):
            self.G[i][0] = 0
        for i in range(1, (self.n) + 1):
            for c in range(1, (self.W) + 1):
                if (self.w[i] > c):
                    self.G[i][c] = self.G[(i - 1)][c]
                else:
                    self.G[i][c] = max(self.G[(i - 1)][c], (self.G[(i - 1)][(c - self.w[i])] + self.v[i]))
        return self.G


if __name__ == '__main__':
    w = [0, 0, 1, 2]
    v = [0, 0, 1, 1]
    W = 10
    prog = Knapsack()
    result = prog.Knapsack(w, v, W)
    print(result)

