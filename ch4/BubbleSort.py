"""Bubble sort: repeated adjacent compare-and-swap until a clean pass.

Auto-generated from BubbleSort.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class BubbleSort:
    def sort(self, A):
        self.A = A
        self.n = len(self.A)
        self.found = True
        while self.found:
            self.found = False
            for j in range(0, (((len(self.A) - 1) - 1)) + 1):
                if (self.A[j] > self.A[(j + 1)]):
                    self.found = True
                    self.A[j], self.A[(j + 1)] = self.A[(j + 1)], self.A[j]


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = BubbleSort()
    prog.sort(A)
    print(A)

