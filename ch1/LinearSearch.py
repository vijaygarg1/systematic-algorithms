"""Return the first index i with A[i] = key, or -1 if absent.

Auto-generated from LinearSearch.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LinearSearch:
    def LinearSearch(self, A, key):
        self.A = A
        self.key = key
        self.n = len(self.A)
        for i in range(0, ((len(self.A) - 1)) + 1):
            if (self.A[i] == self.key):
                return i
        return (-1)


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    key = 0
    prog = LinearSearch()
    result = prog.LinearSearch(A, key)
    print(result)

