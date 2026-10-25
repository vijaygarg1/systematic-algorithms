"""Insertion sort: walk each new element down to its place.

Auto-generated from InsertionSort.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class InsertionSort:
    def InsertionSort(self, A):
        self.A = A
        self.n = len(self.A)
        for i in range(1, ((self.n - 1)) + 1):
            j = (i - 1)
            while (j >= 0):
                if (self.A[j] <= self.A[(j + 1)]):
                    break
                self.A[j], self.A[(j + 1)] = self.A[(j + 1)], self.A[j]
                j = (j - 1)


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = InsertionSort()
    prog.InsertionSort(A)
    print(A)

