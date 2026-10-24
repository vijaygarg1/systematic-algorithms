"""LLP-Sort1 with alternating odd/even-phase scheduling for parallelism.

Auto-generated from LLPOddEvenSort.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPOddEvenSort:
    def LLPOddEvenSort(self, A):
        self.A = A
        self.n = len(self.A)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if ((j < (self.n - 1)) and (self.A[j] > self.A[(j + 1)])):
                    self.A[j], self.A[(j + 1)] = self.A[(j + 1)], self.A[j]
                    _changed = True


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LLPOddEvenSort()
    prog.LLPOddEvenSort(A)
    print(A)

