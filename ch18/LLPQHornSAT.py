"""LLP-QHornSAT: solve q-horn formula by composing HornSAT on X
variables with 2-SAT on Y variables.
This is a composite algorithm, not a single forbidden/advance pair.

Auto-generated from LLPQHornSAT.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPQHornSAT:
    def LLPQHornSAT(self, body1, head1, clauses2, n1, n2):
        self.body1 = body1
        self.head1 = head1
        self.clauses2 = clauses2
        self.n1 = n1
        self.n2 = n2
        self.n = len(self.body1)
        self.G = [False] * (self.n1)
        self.H = [False] * (self.n2)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if self.hornImplied(j):
                    self.G[j] = True
                    _changed = True
        return self.G

    def hornImplied(self, j):
        return ((not self.G[j]) and any(self.allTrue(c) for c in range(0, ((len(self.body1) - 1)) + 1) if (self.head1[c] == j)))

    def allTrue(self, c):
        return all(self.G[self.body1[c][k]] for k in range(0, ((len(self.body1[c]) - 1)) + 1))


if __name__ == '__main__':
    body1 = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    head1 = [3, 1, 6, 1, 6, 3, 6, 4]
    clauses2 = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    n1 = 8
    n2 = 8
    prog = LLPQHornSAT()
    result = prog.LLPQHornSAT(body1, head1, clauses2, n1, n2)
    print(result)

