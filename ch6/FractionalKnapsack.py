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
        for i in range(0, ((self.n - 1)) + 1):
            if (self.w[i] <= self.rem):
                self.x[i] = 1.0
                self.rem = (self.rem - self.w[i])
            else:
                self.x[i] = self.rem / self.w[i]
                break
        return self.x


if __name__ == '__main__':
    v = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    w = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    W = 10.0
    prog = FractionalKnapsack()
    result = prog.solve(v, w, W)
    print(result)

