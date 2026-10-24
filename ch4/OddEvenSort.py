"""Odd-Even Sort: alternating odd- and even-indexed compare-swap passes.

Auto-generated from OddEvenSort.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class OddEvenSort:
    def sort(self, A):
        self.A = A
        self.n = len(self.A)
        self.found = True
        while self.found:
            self.found = False
            j = 1
            while (j < (len(self.A) - 1)):
                if (self.A[j] > self.A[(j + 1)]):
                    self.found = True
                    self.A[j], self.A[(j + 1)] = self.A[(j + 1)], self.A[j]
                j = (j + 2)
            j = 0
            while (j < (len(self.A) - 1)):
                if (self.A[j] > self.A[(j + 1)]):
                    self.found = True
                    self.A[j], self.A[(j + 1)] = self.A[(j + 1)], self.A[j]
                j = (j + 2)


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = OddEvenSort()
    prog.sort(A)
    print(A)

