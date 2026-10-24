"""Return the largest element of A.

Auto-generated from MaxElement.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MaxElement:
    def MaxElement(self, A):
        self.A = A
        self.n = len(self.A)
        max_ = self.A[0]
        for i in range(1, ((len(self.A) - 1)) + 1):
            if (self.A[i] > max_):
                max_ = self.A[i]
        return max_


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = MaxElement()
    result = prog.MaxElement(A)
    print(result)

