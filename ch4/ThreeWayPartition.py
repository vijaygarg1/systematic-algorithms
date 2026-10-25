"""Dutch-flag partition: split A[low..high) into <, =, > pivot regions.
Matches bxx-sort.tex Algorithm ThreeWayPartition (fig:partition) line
for line. The book returns the pair (p, q); LL has no tuple return, so
the two boundary indices come back in a two-slot array instead -- the
only difference between the two.

Auto-generated from ThreeWayPartition.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ThreeWayPartition:
    def ThreeWayPartition(self, A, pivot, low, high):
        self.A = A
        self.pivot = pivot
        self.low = low
        self.high = high
        self.n = len(self.A)
        self.p = self.low
        self.q = self.low
        self.k = self.high
        while (self.q < self.k):
            if (self.A[self.q] < self.pivot):
                self.A[self.p], self.A[self.q] = self.A[self.q], self.A[self.p]
                self.p = (self.p + 1)
                self.q = (self.q + 1)
            else:
                if (self.A[self.q] > self.pivot):
                    self.k = (self.k - 1)
                    self.A[self.q], self.A[self.k] = self.A[self.k], self.A[self.q]
                else:
                    self.q = (self.q + 1)
        result = [0] * (2)
        result[0] = self.p
        result[1] = self.q
        return result


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    pivot = 5
    prog = ThreeWayPartition()
    result = prog.ThreeWayPartition(A, pivot, 0, len(A))
    print(result)

