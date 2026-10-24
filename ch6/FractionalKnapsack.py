"""Fractional knapsack: greedy by value-density v/w (last item may be split).

Auto-generated from FractionalKnapsack.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class FractionalKnapsack:
    def solve(self, v, w, W):
        self.v = v
        self.w = w
        self.W = W
        self.n = len(self.v)
        self.x = [0.0] * (self.n)
        self.rem = self.W
        self.done = False
        self.i = 0
        while ((self.i < self.n) and (not self.done)):
            if (self.w[self.i] <= self.rem):
                self.x[self.i] = 1.0
                self.rem = (self.rem - self.w[self.i])
            else:
                self.x[self.i] = self.rem / self.w[self.i]
                self.done = True
            self.i = (self.i + 1)
        return self.x


if __name__ == '__main__':
    v = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    w = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    W = 10.0
    prog = FractionalKnapsack()
    result = prog.solve(v, w, W)
    print(result)

