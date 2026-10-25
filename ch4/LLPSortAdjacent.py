"""LLP-Sort-Adjacent, exactly as bxx-sort.tex's Algorithm LLP-Sort-Adjacent
states it:

Forbidden (j in [1..n-1]): A[j] > A[j+1]
Advance                  : swap(A[j], A[j+1])   // increment G[j]

G[j] counts the inversions at j; forbidden means G[j] misses one. The
book's [1..n-1] is every index that has a successor; 0-based that is
[0..n-2], so the range itself rules out j = n-1 and the predicate needs
no bound of its own.

Auto-generated from LLPSortAdjacent.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPSortAdjacent:
    def LLPSortAdjacent(self, A):
        self.A = A
        self.n = len(self.A)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 2)) + 1):
                if (self.A[j] > self.A[(j + 1)]):
                    self.A[j], self.A[(j + 1)] = self.A[(j + 1)], self.A[j]
                    _changed = True


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LLPSortAdjacent()
    prog.LLPSortAdjacent(A)
    print(A)

