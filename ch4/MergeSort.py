"""Sequential merge sort, in the book's own shape: each call RETURNS a
newly sorted array rather than sorting a range of G in place, and the
merge step is the sibling MergeTwo program -- `MergeTwo(B, C)`,
compiling to `new MergeTwo().merge(B, C)` -- exactly as Algorithm
MergeSort in bxx-sort.tex writes `Return MergeTwo(B, C)`. That makes

Auto-generated from MergeSort.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MergeTwo:
    def merge(self, B, C):
        self.B = B
        self.C = C
        self.m = len(self.B)
        self.n = len(self.C)
        self.D = [0] * ((self.m + self.n))
        self.i = 0
        self.j = 0
        self.k = 0
        while ((self.i < self.m) and (self.j < self.n)):
            if (self.B[self.i] <= self.C[self.j]):
                self.D[self.k] = self.B[self.i]
                self.i = (self.i + 1)
            else:
                self.D[self.k] = self.C[self.j]
                self.j = (self.j + 1)
            self.k = (self.k + 1)
        while (self.i < self.m):
            self.D[self.k] = self.B[self.i]
            self.i = (self.i + 1)
            self.k = (self.k + 1)
        while (self.j < self.n):
            self.D[self.k] = self.C[self.j]
            self.j = (self.j + 1)
            self.k = (self.k + 1)
        return self.D


class MergeSort:
    def sort(self, A, lo, hi):
        n = len(A)
        if (lo < hi):
            mid = int((lo + hi) / 2)
            B = self.sort(A, lo, mid)
            C = self.sort(A, (mid + 1), hi)
            return MergeTwo().merge(B, C)
        single = [0] * (1)
        single[0] = A[lo]
        return single


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = MergeSort()
    result = prog.sort(A, 0, len(A) - 1)
    print(result)

