"""LLP Horn SAT (bxx-horn.tex Alg LLP-HornSAT). head[c] = clause c's consequent
variable, or -1 for a pure negative (goal) clause. forbidden ranges over
VARIABLES, not clauses; goal clauses are checked once after the least model,
since violating one cannot be repaired by raising G.

Auto-generated from LLPHornSAT.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPHornSAT:
    def LLPHornSAT(self, body, head, n):
        self.body = body
        self.head = head
        self.n = n
        self.G = [False] * (self.n)
        self.hcount = [0] * (self.n)
        self.byHead = [None] * (self.n)
        self.hfill = [0] * (self.n)
        for c in range(0, ((len(self.body) - 1)) + 1):
            if (self.head[c] >= 0):
                self.hcount[self.head[c]] = (self.hcount[self.head[c]] + 1)
        for v in range(0, ((self.n - 1)) + 1):
            self.byHead[v] = [0] * (self.hcount[v])
        for c in range(0, ((len(self.body) - 1)) + 1):
            if (self.head[c] >= 0):
                self.byHead[self.head[c]][self.hfill[self.head[c]]] = c
                self.hfill[self.head[c]] = (self.hfill[self.head[c]] + 1)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self.definiteImplied(j):
                    self.G[j] = True
                    _changed = True
        if self.goalFired(self.body, self.head):
            return None
        return self.G

    def definiteImplied(self, j):
        return ((not self.G[j]) and any(self.allTrue(self.byHead[j][t]) for t in range(0, ((len(self.byHead[j]) - 1)) + 1)))

    def goalFired(self, body, head):
        return any(((head[c] == (0 - 1)) and self.allTrue(c)) for c in range(0, ((len(body) - 1)) + 1))

    def allTrue(self, c):
        return all(self.G[self.body[c][k]] for k in range(0, ((len(self.body[c]) - 1)) + 1))


if __name__ == '__main__':
    body = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    head = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LLPHornSAT()
    result = prog.LLPHornSAT(body, head, len(head))
    print(result)

