"""LLP-Sort2: forbidden when some k > j has A[j] > A[k]; swap them.

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
            for j in range(0, (self.n - 1) + 1):
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

