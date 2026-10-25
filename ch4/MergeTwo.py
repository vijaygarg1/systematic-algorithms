"""MergeTwo: merge two sorted arrays into one, matching bxx-sort.tex
Algorithm MergeTwo (fig:mergetwo) line for line -- including the two
leftover-copy loops, which the book also prints as a single line each.

Auto-generated from MergeTwo.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MergeTwo:
    def MergeTwo(self, B, C):
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


if __name__ == '__main__':
    B = [0, 0, 1, 2]
    C = [0, 0, 1, 1]
    prog = MergeTwo()
    result = prog.MergeTwo(B, C)
    print(result)

