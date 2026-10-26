"""Classical Gale-Shapley man-optimal stable-matching loop.

Auto-generated from GaleShapley.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class GaleShapley:
    def match(self, mpref, rank):
        self.mpref = mpref
        self.rank = rank
        self.n = (len(self.mpref) - 1)
        self.G = [0] * ((self.n + 1))
        self.partner = [0] * ((self.n + 1))
        self.freeMen = [0] * ((self.n + 1))
        self.top = 0
        for i in range(1, (self.n) + 1):
            self.freeMen[self.top] = i
            self.top = (self.top + 1)
        while (self.top > 0):
            self.top = (self.top - 1)
            i = self.freeMen[self.top]
            self.G[i] = (self.G[i] + 1)
            z = self.mpref[i][self.G[i]]
            if (self.partner[z] == 0):
                self.partner[z] = i
            else:
                if (self.rank[z][i] < self.rank[z][self.partner[z]]):
                    self.freeMen[self.top] = self.partner[z]
                    self.top = (self.top + 1)
                    self.partner[z] = i
                else:
                    self.freeMen[self.top] = i
                    self.top = (self.top + 1)
        return self.G


if __name__ == '__main__':
    mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = GaleShapley()
    result = prog.match(mpref, rank)
    print(result)

