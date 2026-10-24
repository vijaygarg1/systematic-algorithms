"""LLP-CountInversions: count inversions to the left of each index.

Auto-generated from LLPCountInversions.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPCountInversions:
    def LLPCountInversions(self, A):
        self.A = A
        self.n = len(self.A)
        self.G = [0] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if (not (self.G[j] >= self.countLeft(self.A, j))):
                    self.G[j] = self.countLeft(self.A, j)
                    _changed = True

    def countLeft(self, A, j):
        count = 0
        for i in range(0, ((j - 1)) + 1):
            if (A[i] > A[j]):
                count = (count + 1)
        return count


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LLPCountInversions()
    prog.LLPCountInversions(A)
    print(A)

