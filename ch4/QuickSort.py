"""QuickSort, exactly as bxx-sort.tex's Algorithm QuickSort states it:

if (low < high) {
pivot  := choosePivot()
(p, q) := Partition(A, pivot, low, high)
QuickSort(A, low, p)
QuickSort(A, q, high)
}

Partition is the sibling ThreeWayPartition program, which returns the
pair (p, q) the book names: A[low..p) < pivot, A[p..q) = pivot, and
A[q..high) > pivot. LL has no tuple, so the pair comes back as a
two-element array and is unpacked into p and q.

high is EXCLUSIVE here -- one past the last slot -- which is what the
book's own text requires: it hands `high` straight to Partition, whose
`k := high; while (q < k)` never reads A[high], and it recurses on
(q, high). The guard `low < high` is the non-empty test for that range.

Termination: the pivot is an element of the range, so the equal block
[p..q) is never empty and both sub-ranges are strictly smaller.

choosePivot takes lo/hi rather than low/high only to keep the generated
main's bound correct: llc.py reads an aux method that also declares
`high` as a signal that the program's high is inclusive, which it is not.

Auto-generated from QuickSort.llp by lang/llcpy.py.
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


class QuickSort:
    def QuickSort(self, A, low, high):
        n = len(A)
        if (low < high):
            pivot = self.choosePivot(A, low, high)
            pq = ThreeWayPartition().ThreeWayPartition(A, pivot, low, high)
            p = pq[0]
            q = pq[1]
            self.QuickSort(A, low, p)
            self.QuickSort(A, q, high)

    def choosePivot(self, A, lo, hi):
        return A[lo]


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = QuickSort()
    prog.QuickSort(A, 0, len(A))
    print(A)

