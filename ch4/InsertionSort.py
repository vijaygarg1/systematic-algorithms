"""Insertion sort: walk each new element down to its place.

Auto-generated from InsertionSort.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class InsertionSort:
    def sort(self, A):
        self.A = A
        self.n = len(self.A)
        for i in range(1, ((len(self.A) - 1)) + 1):
            j = (i - 1)
            done = False
            while ((j >= 0) and (not done)):
                if (self.A[j] <= self.A[(j + 1)]):
                    done = True
                else:
                    self.A[j], self.A[(j + 1)] = self.A[(j + 1)], self.A[j]
                    j = (j - 1)


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = InsertionSort()
    prog.sort(A)
    print(A)

