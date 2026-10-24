"""Count pairs (i, j) with i < j and A[i] + A[j] = target.

Auto-generated from PairSum.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class PairSum:
    def PairSum(self, A, target):
        self.A = A
        self.target = target
        self.n = len(self.A)
        self.count = 0
        for i in range(0, ((len(self.A) - 1)) + 1):
            for j in range((i + 1), ((len(self.A) - 1)) + 1):
                if ((self.A[i] + self.A[j]) == self.target):
                    self.count = (self.count + 1)
        return self.count


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    target = 0
    prog = PairSum()
    result = prog.PairSum(A, target)
    print(result)

