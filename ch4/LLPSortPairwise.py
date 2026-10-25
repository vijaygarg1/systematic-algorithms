"""LLP-Sort-Pairwise, exactly as bxx-sort.tex's Algorithm LLP-Sort-Pairwise
states it:

Forbidden (j in [1..n-1]): exists k > j: A[j] > A[k]   // pick such k
Advance                  : swap(A[j], A[k])

The book writes the witness as "exists k > j" and picks it in a comment;
LL binds it with `pick k`, and bounds it by the array, so the book's
k > j is [j+1..n-1] here. The book's [1..n-1] is [0..n-2] 0-based.

Auto-generated from LLPSortPairwise.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPSortPairwise:
    def LLPSortPairwise(self, A):
        self.A = A
        self.n = len(self.A)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 2)) + 1):
                if self._pick_k(j):
                    self.A[j], self.A[self.k] = self.A[self.k], self.A[j]
                    _changed = True

    def _pick_k(self, j):
        for k in range((j + 1), ((self.n - 1)) + 1):
            if (self.A[j] > self.A[k]):
                self.k = k
                return True
        return False


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LLPSortPairwise()
    prog.LLPSortPairwise(A)
    print(A)

