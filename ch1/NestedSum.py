"""Σ_{i,j < n} i*j computed by a doubly-nested loop.

Auto-generated from NestedSum.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class NestedSum:
    def NestedSum(self, n):
        self.n = n
        self.total = 0
        for i in range(0, ((self.n - 1)) + 1):
            for j in range(0, ((self.n - 1)) + 1):
                self.total = (self.total + (i * j))
        return self.total


if __name__ == '__main__':
    n = 3
    prog = NestedSum()
    result = prog.NestedSum(n)
    print(result)

