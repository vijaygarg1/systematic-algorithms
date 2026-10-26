"""2-approximation vertex cover: greedily pick both endpoints of an
uncovered edge, matching bxx-approximate.tex Algorithm
ApproxVertexCover. The book's input is the edge set E, and the loop
walks it once, which is what makes the algorithm O(|E|).

Auto-generated from ApproxVertexCover.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ApproxVertexCover:
    def ApproxVertexCover(self, eu, ev, n):
        self.eu = eu
        self.ev = ev
        self.n = n
        self.C = [False] * (self.n)
        self.m = len(self.eu)
        self.e = 0
        while (self.e < self.m):
            u = self.eu[self.e]
            v = self.ev[self.e]
            if ((not self.C[u]) and (not self.C[v])):
                self.C[u] = True
                self.C[v] = True
            self.e = (self.e + 1)
        return self.C


if __name__ == '__main__':
    eu = [0, 0, 1, 2]
    ev = [0, 0, 1, 1]
    prog = ApproxVertexCover()
    result = prog.ApproxVertexCover(eu, ev, len(eu))
    print(result)

